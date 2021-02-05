package com.boot.metis.feign.config;

import com.boot.metis.feign.coder.FeignDecoder;
import com.boot.metis.feign.coder.FeignEncoder;
import com.boot.metis.feign.coder.FeignErrorDecoder;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：chenaiwei
 * @Description：FeignConfig
 * @CreateDate：2021/2/1 17:06
 */
@Configuration
@ConditionalOnClass({Feign.class})
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignConfig {
	/*@Bean
	public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
		return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
	}*/

	@Bean
	public Decoder feignDecoder() {
		return new FeignDecoder();
	}

	@Bean
	public Encoder feignEncoder() {
		return new FeignEncoder();
	}

	@Bean
	public ErrorDecoder feignErrorDecoder() {
		return new FeignErrorDecoder();
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	public Request.Options options() {
		return new Request.Options(5000, 5000);
	}
}