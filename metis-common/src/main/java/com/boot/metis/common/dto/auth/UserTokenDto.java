package com.boot.metis.common.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author：chenaiwei
 * @Description：UserTokenDto
 * @CreateDate：2020/7/9 17:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenDto implements Serializable {
	private String userId;
	private String userName;
	private String avatar;
	private RoleDto roleDto;
	private String token;

}
