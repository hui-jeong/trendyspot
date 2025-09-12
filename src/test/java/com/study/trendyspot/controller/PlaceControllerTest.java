package com.study.trendyspot.controller;

import com.study.trendyspot.feign.NaverSearchHelper;
import com.study.trendyspot.feign.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlaceControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    NaverSearchHelper naver;

    @Test
    @DisplayName("detail - 200 OK + 전체 필드(HTML 제거/좌표 파싱 포함)")
    void detail_ok_contract() throws Exception {
        var item = new NaverLocalItem(
                "<b>역삼 카페</b>",
                "http://naver.com",
                "카페",
                "010-0000-0000",
                "서울 강남구",
                "서울 강남구 테헤란호",
                "127.0",
                "37.0"
        );
        var res = new NaverSearchResponse<>("today", 1, 1, 1, List.of(item));

        when(naver.searchLocal("역삼 카페", 5, 1, NaverLocalSortType.RANDOM))
                .thenReturn(res);

        mockMvc.perform(get("/api/places/detail").param("query", "역삼 카페"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("역삼 카페"))      // stripHtml 반영
                .andExpect(jsonPath("$.category").value("카페"))
                .andExpect(jsonPath("$.phone").value("010-0000-0000"))
                .andExpect(jsonPath("$.address").value("서울 강남구"))
                .andExpect(jsonPath("$.roadAddress").value("서울 강남구 테헤란호"))
                .andExpect(jsonPath("$.latitude").value(127.0))              // 좌표 파싱 반영
                .andExpect(jsonPath("$.longitude").value(37.0))
                .andExpect(jsonPath("$.naverLink").value("http://naver.com"));

        verify(naver).searchLocal("역삼 카페", 5, 1, NaverLocalSortType.RANDOM);
        verifyNoMoreInteractions(naver);
    }

    @Test
    @DisplayName("detail - query 누락 400 (헬퍼 미호출)")
    void detail_missingParam_400() throws Exception {
        mockMvc.perform(get("/api/places/detail"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(naver);
    }

    @Test
    @DisplayName("/api/places/detail - 결과 없음 → 서비스 예외 → 5xx")
    void detail_empty_5xx() throws Exception {
        var empty = new NaverSearchResponse<NaverLocalItem>("today", 0, 1, 1, List.of());
        when(naver.searchLocal("없는곳", 5, 1, NaverLocalSortType.RANDOM)).thenReturn(empty);

        var exception = assertThrows(
                Exception.class,
                () -> mockMvc.perform(get("/api/places/detail").param("query", "없는곳")).andReturn()
        );
        Throwable cause = exception.getCause();
        assertInstanceOf(IllegalStateException.class, cause);
        assertTrue(cause.getMessage().contains("장소를 찾을 수 없습니다."));

//        mockMvc.perform(get("/api/places/detail").param("query", "없는곳")).andExpect(result -> {
//                    Throwable ex = result.getResolvedException();
//                    Assertions.assertInstanceOf(IllegalStateException.class, ex);
//                });

        verify(naver).searchLocal("없는곳", 5, 1, NaverLocalSortType.RANDOM);
        verifyNoMoreInteractions(naver);
    }
}
