package com.study.trendyspot.feign.dto;

public record NaverLocalItem(
        String title,
        String link,
        String category,
        String telephone,
        String address,
        String roadAddress,
        String mapx,
        String mapy
){
}
