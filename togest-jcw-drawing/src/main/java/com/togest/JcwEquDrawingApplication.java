package com.togest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringCloudApplication
//@ComponentScan(basePackages={"com.togest.service.impl","com.togest.config","com.togest.web","com.togest.dict.aop"})
@MapperScan(basePackages = "com.togest.dao")
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
public class JcwEquDrawingApplication {
	public static void main(String[] args) {
		SpringApplication.run(JcwEquDrawingApplication.class, args);
	}
}
