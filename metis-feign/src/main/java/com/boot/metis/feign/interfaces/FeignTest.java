package com.boot.metis.feign.interfaces;

import com.boot.metis.common.vo.ResultVo;
import com.boot.metis.feign.config.FeignConfig;
import com.boot.metis.feign.fallback.FeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author：chenaiwei
 * @Description：FeignTest
 * @CreateDate：2021/2/1 17:09
 */
@FeignClient(name = "metis-server",fallbackFactory = FeignFallbackFactory.class,configuration = FeignConfig.class)
public interface FeignTest {
	@GetMapping("/test")
	ResultVo<String> get();
}
