package com.study.trendyspot.feign;

import com.study.trendyspot.feign.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;



@FeignClient(name = "naverSearchClient")
public interface NaverSearchClient {

    @GetMapping("/local.json")
    NaverSearchResponse<NaverLocalItem> searchLocal(@SpringQueryMap NaverParams p);
    @GetMapping("/blog.json")
    NaverSearchResponse<NaverBlogItem> searchBlog(@SpringQueryMap NaverParams p);

    @GetMapping("/news.json")
    NaverSearchResponse<NaverNewsItem> searchNews(@SpringQueryMap NaverParams p);
}
