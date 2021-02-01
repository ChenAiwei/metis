package com.boot.metis.common.component;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.boot.metis.common.dto.MetisResult;
import com.boot.metis.common.dto.PicDtos;
import com.boot.metis.common.dto.PutPolicy;
import com.boot.metis.common.dto.UploadPicDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author：chenaiwei
 * @Description：MetisHelper
 * @CreateDate：2021/1/28 15:41
 */
@Component
@Slf4j
public class MetisHelper {

	@Value("${metis.accessKey}")
	private String accessKey;

	@Value("${metis.secretKey}")
	private String secretKey;

	@Value("${metis.host}")
	private String host;

	@Value("${metis.downloadUrl}")
	private String downloadUrl;

	@Value("${metis.uploadUrl}")
	private String uploadUrl;


	/**
	 * 下载地址
	 *
	 * @param
	 * @param
	 * @param expire
	 * @return
	 */
	public String getDownloadToken(String filePath, long expire) {
		String url = host+downloadUrl +"/"+ filePath + "?e=" + expire;
		String requestUrlWithoutSign = downloadUrl+"/"+ filePath + "?e=" + expire;
		String encodedSign = Base64.encodeBase64URLSafeString(HmacUtils.hmacSha1(secretKey, requestUrlWithoutSign));
		String token = accessKey + ":" + encodedSign;
		url += "&token=" + token;
		return url;
	}


	private String getUploadToken(List<PutPolicy> putPolicy) {
		ObjectMapper mapper = new ObjectMapper();
		String putPolicyJson = null;
		try {
			putPolicyJson = mapper.writeValueAsString(putPolicy);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		String encodedPutPolicy = Base64.encodeBase64URLSafeString(putPolicyJson.getBytes());
		String encodedSign = Base64.encodeBase64URLSafeString(HmacUtils.hmacSha1(secretKey, encodedPutPolicy));
		return accessKey + ":" + encodedSign + ":" + encodedPutPolicy;
	}

	/**
	 * 上传 uploadByStream
	 *
	 * @param stream
	 * @param policyList
	 * @return
	 */
	public MetisResult uploadByStream(InputStream stream, List<PutPolicy> policyList){
		String token = getUploadToken(policyList);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.RFC6532);
		builder.addPart("token", new StringBody(token, ContentType.TEXT_PLAIN));
		builder.addPart("file", new InputStreamBody(stream, ""));
		return this.upload(builder);
	}

	/**
	 * 上传 uploadByFile
	 *
	 * @param file
	 * @param putPolicyList
	 * @return
	 */
	public MetisResult uploadByFile(File file, List<PutPolicy> putPolicyList){
		String token = getUploadToken(putPolicyList);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.RFC6532);
		builder.addPart("token", new StringBody(token, ContentType.TEXT_PLAIN));
		builder.addPart("file", new FileBody(file));
		return this.upload(builder);
	}


	/**
	 * 图片批量上传
	 * @param uploadPicDto
	 * @return
	 */
	private MetisResult uploadPics(UploadPicDto uploadPicDto) {
		List<PutPolicy> putPolicyList = uploadPicDto.getPutPolicyList();
		List<ByteArrayInputStream> streamList = uploadPicDto.getStreamList();
		String token = getUploadToken(putPolicyList);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.RFC6532);
		builder.addPart("token", new StringBody(token, ContentType.TEXT_PLAIN));
		for (int i = 0; i < streamList.size(); i++) {
			builder.addPart("file", new InputStreamBody(streamList.get(i), putPolicyList.get(i).getFileName()));

		}
		return this.upload(builder);
	}

	private MetisResult upload(MultipartEntityBuilder builder){
		HttpPost request = new HttpPost(host+uploadUrl);
		request.setEntity(builder.build());
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpResponse response = httpClient.execute(request);
			return this.handleHttpClientResult(response);
		} catch (IOException e) {
			e.printStackTrace();
			return new MetisResult(-1,e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 上传图片
	 *
	 * @param data
	 * @param bucket 项目类别
	 * @param dir    文件夹
	 * @return
	 * @throws Exception
	 */
	public MetisResult uploadImage(PicDtos data, String bucket, String dir){
		MetisResult metisResult = null;
		if (data != null && CollectionUtil.isNotEmpty(data.getPics())) {
			StringBuffer sb = new StringBuffer();
			String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
			List<ByteArrayInputStream> streamList = new ArrayList<>();
			List<PutPolicy> putPolicyList  = new ArrayList<>();
			data.getPics().forEach(pic ->{
				PutPolicy putPolicy = new PutPolicy();
				Assert.notBlank(pic.getPicData(),"图片内容不能为空");
				Assert.notBlank(pic.getPicName(),"图片名字不能为空");
				byte[] decodeBase64 = Base64.decodeBase64(pic.getPicData());
				ByteArrayInputStream inputStream = new ByteArrayInputStream(decodeBase64);
				String suffix = guessSuffix(inputStream);
				String scope = dir + "/" + today + "/" + UUID.randomUUID().toString() + suffix;
				sb.append(scope + ",");
				putPolicy.setScope(bucket + ":" + scope);
				putPolicy.setFileName(pic.getPicName());
				putPolicy.setDeadline(System.currentTimeMillis() / 1000 + 2000);
				putPolicyList.add(putPolicy);
				streamList.add(inputStream);
			});
			UploadPicDto uploadPicDto = UploadPicDto.builder().streamList(streamList).putPolicyList(putPolicyList).build();
			return this.uploadPics(uploadPicDto);
		} else {
			metisResult = new MetisResult(-1,"上传内容不能为空");
		}
		return metisResult;
	}



	private MetisResult handleHttpClientResult(HttpResponse response) {
		MetisResult metisResult = new MetisResult();
		if (response != null && response.getStatusLine().getStatusCode() == 200) {
			HttpEntity responseEntity = response.getEntity();
			try {
				String content = EntityUtils.toString(responseEntity, "utf-8");
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> map = mapper.readValue(content, Map.class);
				if (map.get("code").toString().equals("0")) {
					metisResult.setCode(0);
					metisResult.setContent(map.get("data"));
				} else {
					metisResult.setCode(-1);
					metisResult.setContent( map.get("msg"));
				}
			} catch (IOException e) {
				e.printStackTrace();
				metisResult.setCode(-1);
				metisResult.setContent(e.getMessage());
			}
		} else {
			metisResult.setCode(-1);
			metisResult.setContent("上传失败");
		}
		return metisResult;
	}

	private String guessSuffix(InputStream is){
		String suffix = ".jpg";
		if (is != null) {
			try {
				String mimeType = URLConnection.guessContentTypeFromStream(is);
				if (mimeType != null) {
					switch (mimeType) {
						case "image/jpg":
							suffix = ".jpg";
							break;
						case "image/jpeg":
							suffix = ".jpg";
							break;
						case "image/png":
							suffix = ".png";
							break;
						case "image/gif":
							suffix = ".gif";
							break;
						case "image/x-bitmap":
							suffix = ".bmp";
							break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return suffix;
	}
}
