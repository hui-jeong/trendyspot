package com.study.trendyspot.service;

import com.study.trendyspot.dto.TrendQueryRequest;
import com.study.trendyspot.dto.TrendResponseDto;

public interface TrendService {
    TrendResponseDto getTrends(TrendQueryRequest request);
}
