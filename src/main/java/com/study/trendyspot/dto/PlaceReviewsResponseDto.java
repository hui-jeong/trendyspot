package com.study.trendyspot.dto;

import java.util.List;

public record PlaceReviewsResponseDto(
        String query,
        List<ReviewItemDto> reviews
) {
}
