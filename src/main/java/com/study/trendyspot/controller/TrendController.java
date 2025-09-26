package com.study.trendyspot.controller;

import com.study.trendyspot.dto.TrendQueryRequest;
import com.study.trendyspot.dto.TrendResponseDto;
import com.study.trendyspot.entity.Granularity;
import com.study.trendyspot.service.TrendService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trends")
@RequiredArgsConstructor
public class TrendController {

    private final TrendService trendService;

    @GetMapping
    public TrendResponseDto getTrends(
            @RequestParam @NotBlank String keyword,
            @RequestParam @NotNull Granularity granularity,
            @RequestParam @NotBlank String from,
            @RequestParam @NotBlank String to,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) List<String> ages
    ){
        return trendService.getTrends(
                new TrendQueryRequest(keyword, granularity, from, to, gender, ages)
        );
    }
}
