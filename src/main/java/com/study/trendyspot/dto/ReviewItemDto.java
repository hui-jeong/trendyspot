package com.study.trendyspot.dto;

public record ReviewItemDto(
        ReviewType type,
        String title,
        String contents,
        String sourceName,
        String link
) {
}
