package com.study.trendyspot.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverFeignConfig implements RequestInterceptor {

    @Value("${naver.search.client-id}")
    String clientId;
    @Value("${naver.search.client-secret}")
    String clientSecret;

    @Override
    public void apply(RequestTemplate t){
        t.header("X-Naver-Client-Id",clientId);
        t.header("X-Naver-Client-Secret",clientSecret);
    }
}
