package com.study.trendyspot.dto;

import java.util.List;

public record TrendResponseDto(
        String keyword,
        String granularity,
        List<Point> points
) {
    public record Point(String date, double index) {}
}
