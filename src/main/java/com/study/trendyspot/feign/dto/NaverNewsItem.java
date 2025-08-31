package com.study.trendyspot.feign.dto;

public record NaverNewsItem(
        String title,
        String originallink,
        String link,
        String description,
        String pubDate
) {
}
