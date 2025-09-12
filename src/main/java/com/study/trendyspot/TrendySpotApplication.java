package com.study.trendyspot;

import com.study.trendyspot.properties.ClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@ConfigurationPropertiesScan(basePackages = "com.study.trendyspot")
public class TrendySpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrendySpotApplication.class, args);
	}

}
