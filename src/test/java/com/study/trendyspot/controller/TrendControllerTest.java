package com.study.trendyspot.controller;

import com.study.trendyspot.entity.Granularity;
import com.study.trendyspot.feign.NaverDataLabHelper;
import com.study.trendyspot.feign.dto.NaverDataLabResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TrendControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    NaverDataLabHelper naverDataLabHelper;

    @Test
    @DisplayName("통합 /api/trends 호출 시 helper가 네이버 응답을 준다고 가정 -200ok")
    void getTrends_integration_ok() throws Exception{
        var data = List.of(
                new NaverDataLabResponse.Result.Data("2025-08-01",70.0),
                new NaverDataLabResponse.Result.Data("2025-08-02",72.0)
        );
        var result = new NaverDataLabResponse.Result("강남 카페",List.of("강남 카페"),data);
        var feignResponse = new NaverDataLabResponse("2025-08-01","2025-08-31","date",List.of(result));

        given(naverDataLabHelper.searchTrends(
                anyString(),anyString(),anyString(),any(Granularity.class),any(),anyList()
        )).willReturn(feignResponse);

        mockMvc.perform(get("/api/trends")
                        .param("keyword","강남 카페")
                        .param("granularity", Granularity.DAILY.name())
                        .param("from", "2025-08-01")
                        .param("to", "2025-08-31")
                        .param("gender","f")
                        .param("ages","2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keyword").value("강남 카페"))
                .andExpect(jsonPath("$.points.length()").value(2));
    }

    @Test
    @DisplayName("헬퍼가 예외를 던짐 - 500")
    void getTrends_helperThrows_returns500() throws Exception {
        given(naverDataLabHelper.searchTrends(
                anyString(), anyString(), anyString(), any(Granularity.class),
                any(), anyList()
        )).willThrow(new RuntimeException("NAVER DataLab down"));

        mockMvc.perform(get("/api/trends")
                        .param("keyword", "강남 카페")
                        .param("granularity", Granularity.DAILY.name())
                        .param("from", "2025-08-01")
                        .param("to", "2025-08-31")
                        .param("gender","f")
                        .param("ages","2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}
