package com.boot.metis.common.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author：chenaiwei
 * @Description：UpToken
 * @CreateDate：2021/1/27 14:24
 */
@Data
public class UpToken {

	private String accessKey;

	private List<PutPolicy> putPolicy;

	private String encodedSign;

}
