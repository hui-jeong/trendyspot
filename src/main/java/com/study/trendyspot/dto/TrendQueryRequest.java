package com.study.trendyspot.dto;

import com.study.trendyspot.entity.Granularity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record TrendQueryRequest(
        @NotBlank String keyword,
        @NotNull Granularity granularity,
        @NotBlank  String from,
        @NotBlank  String to,
        String gender,
        List<String> ages

) {
}
