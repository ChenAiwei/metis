package com.boot.metis.server.restful;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.boot.metis.common.util.ResultVoUtil;
import com.boot.metis.common.vo.ResultVo;
import com.boot.metis.feign.interfaces.FeignTest;
import com.boot.metis.server.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：chenaiwei
 * @Description：TestController
 * @CreateDate：2021/2/2 14:33
 */
@RefreshScope
@RestController
public class TestController implements FeignTest {
	@Value("${userName:123456}")
	private String userName;

	@Override
	public ResultVo<String> get() {
		return ResultVoUtil.success(userName);
	}
}
