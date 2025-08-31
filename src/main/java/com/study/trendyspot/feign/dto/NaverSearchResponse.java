package com.study.trendyspot.feign.dto;

import java.util.List;

public record NaverSearchResponse<T> (
    String lastBuildDate,
    int total,
    int start,
    int display,
    List<T> items
){
}
