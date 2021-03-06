package com.boot.metis.server.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：aiwei
 * @Description：MybatisPlusConfig
 */
@Configuration
@MapperScan("com.boot.metis.server.mapper*")
public class MybatisPlusConfig {

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * 乐观锁 插件
	 * 
	 * @return
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLoker() {
		return new OptimisticLockerInterceptor();
	}

}
