package com.boot.metis.helper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：chenaiwei
 * @Description：HttpClientResult
 * @CreateDate：2021/1/29 9:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetisResult {
	/**
	 * 响应状态码
	 */
	private int code;

	/**
	 * 响应数据
	 */
	private Object content;

	private Long timeStamp = System.currentTimeMillis();

	public MetisResult(int code) {
		this.code = code;
	}

	public MetisResult(int code, Object content) {
		this.code = code;
		this.content = content;
	}
}
