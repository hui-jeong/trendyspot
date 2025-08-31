package com.study.trendyspot.service;

import com.study.trendyspot.dto.PlaceDetailResponseDto;
import com.study.trendyspot.feign.NaverSearchClient;
import com.study.trendyspot.feign.dto.NaverLocalItem;
import com.study.trendyspot.feign.dto.NaverSearchResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlaceServiceImplTest {
    @Test
    void getPlaceDetail() {
        // 가짜 FeignClient mock 만들기
        NaverSearchClient mockClient = Mockito.mock(NaverSearchClient.class);
        PlaceService service = new PlaceServiceImpl(mockClient);

        //가짜 응답 설정
        NaverLocalItem fakeItem = new NaverLocalItem(
                "역삼 카페","http://naver.com", "카페", "010-0000-0000", "서울 강남구", "서울 강남구 테헤란로", "127.0","37.0"
        );
        Mockito.when(mockClient.searchLocal("역삼 카페",5,1,"sim")).thenReturn(new NaverSearchResponse<>("today",1,1,5, List.of(fakeItem)));

        //서비스 메서드 실행
        PlaceDetailResponseDto result = service.getPlaceDetail("역삼 카페",null,null);

        assertThat(result.name()).isEqualTo("역삼 카페");
        assertThat(result.phone()).isEqualTo("010-0000-0000");
    }
}