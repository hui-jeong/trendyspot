package com.study.trendyspot.feign;

import com.study.trendyspot.properties.ClientProperties;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverFeignConfig {
    @Bean feign.Logger.Level feignLoggerLevel() { return feign.Logger.Level.FULL; }
    @Bean
    public RequestInterceptor naverHeaders(ClientProperties props){
        return template -> {
            template.header("X-Naver-Client-Id", props.clientId());
            template.header("X-Naver-Client-Secret",props.clientSecret());
        };
    }
    @Bean
    public RequestInterceptor debugPrint() {
        return t -> System.out.println("[FEIGN] " + t.method() + " " + t.path() + " " + t.queries());
    }
}