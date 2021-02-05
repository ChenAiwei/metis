package com.boot.metis.helper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @Author：chenaiwei
 * @Description：UploadPicDto
 * @CreateDate：2021/1/29 15:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadPicDto {
	private List<ByteArrayInputStream> streamList;
	private List<PutPolicy> putPolicyList;
}
