package com.boot.metis.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author：chenaiwei
 * @Description：GateWayApplication
 * @CreateDate：2021/2/8 9:08
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GateWayApplication.class, args);
	}
}
