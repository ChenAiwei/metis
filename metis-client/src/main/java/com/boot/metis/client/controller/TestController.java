package com.boot.metis.client.controller;

import com.boot.metis.common.vo.ResultVo;
import com.boot.metis.feign.interfaces.FeignTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：chenaiwei
 * @Description：TestController
 * @CreateDate：2021/2/1 17:12
 */
@RestController
@RequestMapping("test")
public class TestController {

	@Autowired
	private FeignTest feignTest;

	@GetMapping("/get")
	public ResultVo<?> get(){
		return feignTest.get();
	}
}
