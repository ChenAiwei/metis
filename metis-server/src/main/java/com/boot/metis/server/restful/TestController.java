package com.boot.metis.server.restful;

import com.boot.metis.common.util.ResultVoUtil;
import com.boot.metis.common.vo.ResultVo;
import com.boot.metis.feign.interfaces.FeignTest;
import com.boot.metis.server.exception.ServiceException;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：chenaiwei
 * @Description：TestController
 * @CreateDate：2021/2/2 14:33
 */
@RestController
public class TestController implements FeignTest {
	@Override
	public ResultVo<Long> get() {
		int i = 1/0;
		return ResultVoUtil.success(System.currentTimeMillis());
	}
}
