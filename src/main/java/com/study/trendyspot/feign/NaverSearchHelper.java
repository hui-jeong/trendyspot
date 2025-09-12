package com.study.trendyspot.feign;

import com.study.trendyspot.feign.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverSearchHelper {

    private static final int DEFAULT_START = 1;

    private final NaverSearchClient client;

    public NaverSearchResponse<NaverLocalItem> searchLocal(String query, Integer display, Integer start, NaverLocalSortType sort){
        //return client.searchLocal(new NaverParams(query, display, null, null));
        return client.searchLocal(new NaverParams(query, display, defaultStart(start), sort.value()));
    }
    public NaverSearchResponse<NaverBlogItem> searchBlog(String query, Integer display, Integer start, NaverBlogSortType sort){
        return client.searchBlog(new NaverParams(query, display, defaultStart(start), sort.value()));
    }
    public NaverSearchResponse<NaverNewsItem> searchNews(String query, Integer display, Integer start, NaverBlogSortType sort){
        return client.searchNews(new NaverParams(query, display, defaultStart(start), sort.value()));
    }
    private static int defaultStart(Integer start) {
        return (start == null || start < 1) ? DEFAULT_START : start;
    }
}
