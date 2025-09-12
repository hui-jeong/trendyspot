package com.study.trendyspot.controller;

import com.study.trendyspot.dto.PlaceDetailResponseDto;
import com.study.trendyspot.dto.PlaceReviewsResponseDto;
import com.study.trendyspot.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/places")
public class PlaceController {
    private final PlaceService placeService; // 인터페이스 의존

    @GetMapping("/detail")
    public ResponseEntity<PlaceDetailResponseDto> detail(
            @RequestParam String query,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ) {
        return ResponseEntity.ok(placeService.getPlaceDetail(query, latitude, longitude));
    }

    @GetMapping("/reviews")
    public ResponseEntity<PlaceReviewsResponseDto> reviews(
            @RequestParam String query,
            @RequestParam(defaultValue = "5") int blogN,
            @RequestParam(defaultValue = "5") int newsN
    ) {
        return ResponseEntity.ok(placeService.getPlaceReviews(query, blogN, newsN));
    }
}
