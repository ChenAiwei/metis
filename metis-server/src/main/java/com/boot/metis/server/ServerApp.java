package com.boot.metis.server;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author：chenaiwei
 * @Description：ServerApp
 * @CreateDate：2020/7/8 9:00
 */
@SpringCloudApplication
@EnableDiscoveryClient
public class ServerApp {
	public static void main(String[] args) {
		SpringApplication.run(ServerApp.class, args);
	}
}
