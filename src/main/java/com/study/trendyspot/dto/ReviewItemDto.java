package com.study.trendyspot.dto;

public record ReviewItemDto(
        String type, // 블로그인지 뉴스인지
        String title,
        String contents, //내용
        String sourceName, //출처
        String link
) {
}
