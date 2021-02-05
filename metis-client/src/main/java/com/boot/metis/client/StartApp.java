package com.boot.metis.client;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author：chenaiwei
 * @Description：StartApp
 * @CreateDate：2020/7/8 9:00
 */
@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.boot.metis.feign.interfaces")
@ComponentScan(basePackages = {"com.boot.metis.client","com.boot.metis.feign"})
public class StartApp {
	public static void main(String[] args) {
		SpringApplication.run(StartApp.class, args);
	}
}
