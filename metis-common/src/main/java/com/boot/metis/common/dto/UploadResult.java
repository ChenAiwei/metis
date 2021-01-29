package com.boot.metis.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：chenaiwei
 * @Description：UploadResult
 * @CreateDate：2021/1/29 16:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResult {
	private String fileName;
	private String filePath;
}
