package com.boot.metis.feign.interfaces;

import com.boot.metis.common.dto.auth.UserTokenDto;
import com.boot.metis.common.vo.ResultVo;
import com.boot.metis.feign.config.FeignConfig;
import com.boot.metis.feign.fallback.LoginFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author：chenaiwei
 * @Description：Login
 * @CreateDate：2021/3/25 10:39
 */
@FeignClient(name = "metis-server", fallbackFactory = LoginFallbackFactory.class, configuration = FeignConfig.class)
public interface Login {
	/**
	 * 登陆
	 *
	 * @param userAccount
	 * @param passWord
	 * @param ip
	 * @return
	 */
	@GetMapping("/login")
	ResultVo<UserTokenDto> login(@RequestParam("userAccount") String userAccount, @RequestParam("passWord") String passWord, @RequestParam("ip") String ip) throws UnsupportedEncodingException, NoSuchAlgorithmException;

	/**
	 * 登出
	 *
	 * @param userId
	 */
	@GetMapping("/logout")
	ResultVo<?> logout(@RequestParam("userId") String userId);

	/**
	 * 注册
	 *
	 * @param username
	 * @param password
	 */
	@GetMapping("/register")
	ResultVo<?> register(@RequestParam("username") String username, @RequestParam("password") String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
