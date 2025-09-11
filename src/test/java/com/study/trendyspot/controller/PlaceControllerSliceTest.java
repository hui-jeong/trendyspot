package com.study.trendyspot.controller;

import com.study.trendyspot.dto.PlaceDetailResponseDto;
import com.study.trendyspot.service.PlaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceController.class)
class PlaceControllerSliceTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PlaceService placeService;

    @Test
    @DisplayName("GET / api/places/detail - 200 ok , 핵심 필드 확인")
    void detailApi_return() throws Exception{

        when(placeService.getPlaceDetail("역삼 카페",null,null))
                .thenReturn(new PlaceDetailResponseDto(
                        "역삼 카페","카페","010-0000-0000","서울 강남구","서울 강남구 테헤란호",127.0,37.0,"http://naver.com"
                ));


        mockMvc.perform(get("/api/places/detail").param("query","역삼 카페"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("역삼 카페"));

        verify(placeService).getPlaceDetail("역삼 카페", null, null);
        verifyNoMoreInteractions(placeService);
    }

    @Test
    @DisplayName("query파라미터 누락 400 Request")
    void detailApi_missingQuery_400()throws Exception{
        mockMvc.perform(get("/api/places/detail"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(placeService);
    }


    @Test
    @DisplayName("서비스 예외 발생 5xx")
    void detailApi_serviceThrows_5xx() throws Exception{
        doThrow(new RuntimeException("error"))
                .when(placeService).getPlaceDetail("역삼 카페",null,null);

//        var exception = assertThrows(RuntimeException.class,()->{throw new RuntimeException("테스트 예외 메시지");});
//        assertEquals("테스트 예외 메시지", exception.getMessage());

        mockMvc.perform(get("/api/places/detail").param("query","역삼 카페"))
                .andExpect(status().is5xxServerError())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof RuntimeException))
                .andExpect(result ->
                        assertEquals("error", result.getResolvedException().getMessage()));


        verify(placeService).getPlaceDetail("역삼 카페", null, null);
        verifyNoMoreInteractions(placeService);
    }
}



