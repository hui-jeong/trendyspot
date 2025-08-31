package com.study.trendyspot.dto;

public record PlaceDetailResponseDto(
        String name,
        String category,
        String phone,
        String address,
        String roadAddress,
        Double latitude,
        Double longitude,
        String naverLink
) {
}
