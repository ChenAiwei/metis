package com.boot.metis.feign.fallback;

import com.boot.metis.common.util.ResultVoUtil;
import com.boot.metis.common.vo.ResultVo;
import com.boot.metis.feign.interfaces.FeignTest;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：chenaiwei
 * @Description：FeignFallbackFactory
 * @CreateDate：2021/2/2 8:51
 */
@Component
public class FeignFallbackFactory implements FallbackFactory<FeignTest>{
	@Override
	public FeignTest create(Throwable throwable) {
		throwable.printStackTrace();
		return new FeignTest() {
			@Override
			public ResultVo<String> get() {
				return ResultVoUtil.error(throwable.toString());
			}
		};
	}
}
