package com.boot.metis.feign.coder;

import com.boot.metis.feign.exception.FeignException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static feign.FeignException.errorStatus;

/**
 * @Author：chenaiwei
 * @Description：FeignErrorDecoder
 * @CreateDate：2020/10/14 15:55
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {
		log.info("feign client response:", response);
		return errorStatus(methodKey, response);
	}
}
