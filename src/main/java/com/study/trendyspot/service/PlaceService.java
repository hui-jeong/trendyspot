package com.study.trendyspot.service;

import com.study.trendyspot.dto.PlaceDetailResponseDto;
import com.study.trendyspot.dto.PlaceReviewsResponseDto;

public interface  PlaceService {
    PlaceDetailResponseDto getPlaceDetail(String query, Double latitude, Double longitude);
    PlaceReviewsResponseDto getPlaceReviews(String query, int blogN, int newsN);
}
