package com.study.trendyspot.feign;

import com.study.trendyspot.feign.dto.NaverBlogItem;
import com.study.trendyspot.feign.dto.NaverLocalItem;
import com.study.trendyspot.feign.dto.NaverNewsItem;
import com.study.trendyspot.feign.dto.NaverSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "naverSearchClient",
        url = "${naver.search.base-url}",
        configuration = NaverFeignConfig.class
)
public interface NaverSearchClient {

    //지역 검색
    @GetMapping("/local.json")
    NaverSearchResponse<NaverLocalItem> searchLocal(
            @RequestParam("query") String query,
            @RequestParam(value="display",required=false) Integer display,
            @RequestParam(value="start", required=false) Integer start,
            @RequestParam(value="sort", required=false) String sort
    );

    @GetMapping("/blog.json")
    NaverSearchResponse<NaverBlogItem> searchBlog(
            @RequestParam("query") String query,
            @RequestParam(value="display", required=false) Integer display,
            @RequestParam(value="start", required=false) Integer start,
            @RequestParam(value="sort", required=false) String sort
    );

    @GetMapping("/news.json")
    NaverSearchResponse<NaverNewsItem> searchNews(
            @RequestParam("query") String query,
            @RequestParam(value="display", required=false) Integer display,
            @RequestParam(value="start", required=false) Integer start,
            @RequestParam(value="sort", required=false) String sort
    );
}
