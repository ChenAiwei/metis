package com.boot.metis.server.service;

import com.boot.metis.common.dto.PutPolicy;
import com.boot.metis.common.dto.UploadResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangjun
 * @creation_date 2017/3/10
 * @version 1.0
 */
@Component
public class UploadFileService {

	private static final String SYMBOL = ":";

    public List<UploadResult> upload(List<PutPolicy> policyList, MultipartFile[] file, String pathPrefix) {
		List<UploadResult> resultList = new ArrayList<>();
		for (int i = 0; i < file.length; i++) {
			String path = gainUserPath(policyList.get(i).getScope());
			String filePath = pathPrefix + path;
			try {
				File newFile = new File(filePath);
				if (!newFile.exists()) {
					newFile.mkdirs();
					file[i].transferTo(newFile);
					resultList.add(UploadResult.builder().fileName(policyList.get(i).getFileName()).filePath(path).build());
				}
			} catch (IOException e) {
			}
		}
        return resultList;
    }


    /**
     *  解析并获取上传的目标资源空间
     * @param scope
     * @return
     */
    private static String gainUserPath(String scope) {
        //scope = bucket : fileKey   a/b/c      /bucket/a/b/c
        StringBuilder sb = new StringBuilder("");
        if (scope.contains(SYMBOL)) {
            String[] filePaths = scope.split(SYMBOL);
            sb.append(filePaths[0]).append(File.separator);
            sb.append(filePaths[1].replace("/", File.separator));
        }
        return sb.toString();
    }
}
