package com.study.trendyspot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "naver.search")
public record ClientProperties(String clientId, String clientSecret) {
}
