package com.study.trendyspot.feign.dto;

public record NaverBlogItem(
        String title,
        String link,
        String description,
        String bloggername,
        String bloggerlink,
        String postdate
) {
}
