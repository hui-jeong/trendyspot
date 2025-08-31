package com.study.trendyspot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.study.trendyspot.feign")
public class TrendySpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrendySpotApplication.class, args);
	}

}
