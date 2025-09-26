package com.study.trendyspot.controller;

import com.study.trendyspot.dto.TrendQueryRequest;
import com.study.trendyspot.dto.TrendResponseDto;
import com.study.trendyspot.entity.Granularity;
import com.study.trendyspot.service.TrendService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TrendController.class)
class TrendControllerSliceTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    TrendService trendService;

    @Test
    @DisplayName("GET /api/trends - 200 ok, 트렌트 포인트 반환")
    void getTrends_ok() throws Exception{
        var points = List.of(
                new TrendResponseDto.Point("2025-08-01",70.0),
                new TrendResponseDto.Point("2025-08-02",72.0)
        );
        var response = new TrendResponseDto("강남 카페","DAILY",points);

        given(trendService.getTrends(ArgumentMatchers.any(TrendQueryRequest.class)))
                .willReturn(response);

        mockMvc.perform(get("/api/trends")
                        .param("keyword","강남 카페")
                        .param("granularity", Granularity.DAILY.name())
                        .param("from","2025-08-01")
                        .param("to","2025-08-31")
                        .param("gender","f")
                        .param("ages","3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keyword").value("강남 카페"))
                .andExpect(jsonPath("$.granularity").value("DAILY"))
                .andExpect(jsonPath("$.points[0].date").value("2025-08-01"))
                .andExpect(jsonPath("$.points[0].index").value(70.0));
    }

    @Test
    @DisplayName("keyword 누락 - 400 에러")
    void getTrends_keywordMisiing_400() throws Exception{
        mockMvc.perform(get("/api/trends")
                        .param("granularity",Granularity.DAILY.name())
                        .param("from","2025-08-01")
                        .param("to","2025-08-31")
                        .param("gender","f")
                        .param("ages","3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("바인딩 실패: granularity 오타 - 400 에러")
    void getTrends_granularityInvalid_400() throws Exception{
        mockMvc.perform(get("/api/trends")
                        .param("keyword", "강남 카페")
                        .param("granularity", "DAILYY")
                        .param("from", "2025-08-01")
                        .param("to", "2025-08-31")
                        .param("gender","f")
                        .param("ages","3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
