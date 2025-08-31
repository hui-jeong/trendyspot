package com.study.trendyspot.controller;

import com.study.trendyspot.dto.PlaceDetailResponseDto;
import com.study.trendyspot.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceController.class)
class PlaceControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PlaceService placeService;

    @Test
    void detailApi_return() throws Exception{
        //가짜 서비스 응답
        when(placeService.getPlaceDetail("역삼 카페",null,null))
                .thenReturn(new PlaceDetailResponseDto(
                        "역삼 카페","카페","010-0000-0000","서울 강남구","서울 강남구 테헤란호",127.0,37.0,"http://naver.com"
                ));

        //실제
        mockMvc.perform(get("/api/places/detail").param("query","역삼 카페"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("역삼 카페"));
    }
}
