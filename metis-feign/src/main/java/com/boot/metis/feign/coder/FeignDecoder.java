package com.boot.metis.feign.coder;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Author：chenaiwei
 * @Description：FeignDecoder
 * @CreateDate：2020/11/13 10:04
 */
public class FeignDecoder implements Decoder {
	private JacksonDecoder decoder;
	public FeignDecoder(){
		decoder = new JacksonDecoder();
	}
	@Override
	public Object decode(Response response, Type type) throws IOException{
		return decoder.decode(response,type);
	}
}
