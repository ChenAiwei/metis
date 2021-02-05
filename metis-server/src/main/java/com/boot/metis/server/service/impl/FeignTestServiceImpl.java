package com.boot.metis.server.service.impl;

import com.boot.metis.common.util.ResultVoUtil;
import com.boot.metis.common.vo.ResultVo;
import com.boot.metis.feign.interfaces.FeignTest;
import org.springframework.stereotype.Service;

/**
 * @Author：chenaiwei
 * @Description：FeignTestServiceImpl
 * @CreateDate：2021/2/2 11:10
 */
@Service
public class FeignTestServiceImpl implements FeignTest {
	@Override
	public ResultVo<Long> get() {
		return ResultVoUtil.success(System.currentTimeMillis());
	}
}
