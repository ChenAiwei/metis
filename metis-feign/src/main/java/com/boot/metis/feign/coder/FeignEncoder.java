package com.boot.metis.feign.coder;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;

/**
 * @Author：chenaiwei
 * @Description：FeignEncoder
 * @CreateDate：2020/11/13 10:00
 */
public class FeignEncoder implements Encoder {
	private JacksonEncoder encoder;
	public FeignEncoder(){
		encoder = new JacksonEncoder();
	}
	@Override
	public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
		encoder.encode(object,bodyType,template);
	}
}
