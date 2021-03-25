package com.boot.metis.web.controller;

import com.boot.metis.common.dto.auth.UserTokenDto;
import com.boot.metis.common.util.ResultVoUtil;
import com.boot.metis.common.vo.ResultVo;
import com.boot.metis.feign.interfaces.Login;
import com.boot.metis.web.utils.IpUtil;
import com.boot.metis.web.vo.LoginVo;
import com.boot.metis.web.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author：chenaiwei
 * @Description：LoginController
 * @CreateDate：2021/3/25 10:13
 */
@RestController
@Slf4j
public class LoginController {

	@Autowired
	private Login login;

	@PostMapping("/login")
	public ResultVo<UserTokenDto> login(@RequestBody @Valid LoginVo loginVo, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return login.login(loginVo.getUserAccount(),loginVo.getPassWord(), IpUtil.getIpAddress(request));
	}

	@GetMapping("/logout/{userId}")
	public ResultVo<?> logout(@PathVariable String userId){
		return login.logout(userId);
	}

	@PostMapping("/register")
	public ResultVo<?> register(@RequestBody @Valid RegisterVo registerVo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return login.register(registerVo.getUserAccount(),registerVo.getPassWord());
	}
}
