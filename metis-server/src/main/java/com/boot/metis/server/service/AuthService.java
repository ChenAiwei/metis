package com.boot.metis.server.service;

import com.boot.metis.common.dto.Auth;

/**
 * @Author：chenaiwei
 * @Description：AuthService
 * @CreateDate：2021/1/27 14:24
 */
public interface AuthService {

	Auth getAuthInfo(String accessKey);

}