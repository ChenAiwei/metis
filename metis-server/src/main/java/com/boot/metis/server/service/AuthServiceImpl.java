package com.boot.metis.server.service;

import com.boot.metis.common.dto.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：chenaiwei
 * @Description：AuthServiceImpl
 * @CreateDate：2021/1/27 14:33
 */
@Service
public class AuthServiceImpl implements AuthService {

	private Map<String, Auth> data = new HashMap<String, Auth>();

	@Value("${metis.accessKey}")
	private String accessKey;

	@Value("${metis.secretKey}")
	private String secretKey;

	@PostConstruct
	public void init(){
		Auth au = new Auth();
		au.setUser("metis");
		au.setAccessKey(accessKey);
		au.setSecretKey(secretKey);
		data.put(accessKey,au);
	}
	@Override
	public Auth getAuthInfo(String accessKey) {
		return data.get(accessKey);
	}
}
