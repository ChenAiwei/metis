package com.boot.metis.common.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author：chenaiwei
 * @Description：RoleDto
 * @CreateDate：2021/3/25 10:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {
	private Long roleId;
	private String roleName;
	private Boolean admin;
}
