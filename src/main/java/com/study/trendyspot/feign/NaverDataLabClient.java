package com.study.trendyspot.feign;

import com.study.trendyspot.feign.dto.NaverDataLabRequest;
import com.study.trendyspot.feign.dto.NaverDataLabResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "naverDataLabClient")
public interface NaverDataLabClient {
    @PostMapping(consumes = "application/json")
    NaverDataLabResponse searchTrends(@RequestBody NaverDataLabRequest body);
}
