package com.boot.metis.helper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * @Author：chenaiwei
 * @Description：PutPolicy
 * @CreateDate：2021/1/27 14:24
 */
@Data
@JsonInclude(Include.NON_NULL)
public class PutPolicy {
	private String scope;
	private String fileName;
	private Long deadline;
	private String returnUrl;
	private String returnBody;
	private String callbackUrl;
	private String callbackBody;
	private String callbackBodyType;
}
