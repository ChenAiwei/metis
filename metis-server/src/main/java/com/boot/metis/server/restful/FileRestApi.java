package com.boot.metis.server.restful;

import com.boot.metis.common.dto.PutPolicy;
import com.boot.metis.common.dto.UpToken;
import com.boot.metis.common.dto.UploadResult;
import com.boot.metis.server.exception.ServiceException;
import com.boot.metis.server.service.MetisService;
import com.boot.metis.server.service.UploadFileService;
import com.boot.metis.server.util.FileUtils;
import com.boot.metis.server.util.ResultVoUtil;
import com.boot.metis.server.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：chenaiwei
 * @Description：FileRestApi
 * @CreateDate：2021/1/27 14:24
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileRestApi {

	@Autowired
	private MetisService metisService;

	@Autowired
	private UploadFileService uploadFileService;

	@Value("${metis.hidePath}")
	private String hidePath;

	@PostMapping(value = "/upload")
	public ResultVo<List<UploadResult>> upload(@RequestParam("token") String token, @RequestParam("file") MultipartFile[] file) {
		List<String> nameLists = Arrays.asList(file).stream().map(p -> p.getOriginalFilename()).collect(Collectors.toList());
		log.info("上传文件开始：{}",nameLists);
		UpToken tokenDecrypt = metisService.analyzeUpToken(token);
		List<PutPolicy> putPolicyList = tokenDecrypt.getPutPolicy();
		StringBuilder prefix = new StringBuilder(hidePath);
		List<UploadResult> uploadResultList = uploadFileService.upload(putPolicyList, file, prefix.toString());
		log.info("上传文件结束：{}",nameLists);
		return ResultVoUtil.success(uploadResultList);
	}

	@GetMapping(value = "/download/{domain}/{bucket}/**")
	public void download(@PathVariable String domain, @PathVariable String bucket, @RequestParam("token") String token, @RequestParam("e") long expire, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		String fileName = new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		if(StringUtils.isEmpty(fileName)) {
			throw new ServiceException("文件名称不能为空");
		}
		String requestUrlWithoutToken = URLDecoder.decode(request.getRequestURI().toString(), "utf-8") + "?e=" + expire;
		if (System.currentTimeMillis() / 1000 > expire) {
			throw new ServiceException("凭证已过期");
		}
		metisService.analyzeDownToken(token, requestUrlWithoutToken);
		if (System.currentTimeMillis() / 1000 > expire) {
			throw new ServiceException("凭证已过期");
		}
		StringBuilder path = new StringBuilder(hidePath).append(domain).append(File.separator).append(bucket).append(File.separator).append(fileName);
		FileUtils.downloadFile(response, new File(path.toString()));
	}

}
