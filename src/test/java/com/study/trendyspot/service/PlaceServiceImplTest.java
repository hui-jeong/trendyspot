package com.study.trendyspot.service;

import com.study.trendyspot.dto.PlaceDetailResponseDto;
import com.study.trendyspot.dto.PlaceReviewsResponseDto;
import com.study.trendyspot.feign.NaverSearchClient;
import com.study.trendyspot.feign.NaverSearchHelper;
import com.study.trendyspot.feign.dto.NaverLocalItem;
import com.study.trendyspot.feign.dto.NaverLocalSortType;
import com.study.trendyspot.feign.dto.NaverSearchResponse;
import com.study.trendyspot.mapper.ReviewMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
class PlaceServiceImplTest {

    @Mock
    NaverSearchHelper naver;

    @Mock
    ReviewMapper reviewMapper;

    @InjectMocks
    PlaceServiceImpl placeService;

    @Test
    @DisplayName("getPlaceDetail - 첫 아이템 매핑 성공, 좌표/html 제거")
    void getPlaceDetail() {

        NaverLocalItem item = new NaverLocalItem(
                "<b>역삼 카페</b>","http://naver.com","카페","010-0000-0000","서울 강남구","서울 강남구 테헤란로","127.0","37.0"
        );
        NaverSearchResponse<NaverLocalItem> response = new NaverSearchResponse<>("today",1,1,1,List.of(item));

        when(naver.searchLocal("역삼 카페",5,1, NaverLocalSortType.RANDOM)).thenReturn(response);

        PlaceDetailResponseDto dto = placeService.getPlaceDetail("역삼 카페",null,null);

        assertAll(
                () -> assertNotNull(dto),
                () -> assertEquals("역삼 카페", dto.name(), "HTML 제거 확인"),
                () -> assertEquals("카페", dto.category()),
                () -> assertEquals("010-0000-0000", dto.phone()),
                () -> assertEquals("서울 강남구", dto.address()),
                () -> assertEquals("서울 강남구 테헤란로", dto.roadAddress()),
                () -> assertEquals(127.0, dto.latitude()),
                () -> assertEquals(37.0, dto.longitude()),
                () -> assertEquals("http://naver.com", dto.naverLink())
        );

        verify(naver).searchLocal("역삼 카페", 5, 1, NaverLocalSortType.RANDOM);
        verifyNoMoreInteractions(naver, reviewMapper);
    }

    @Test
    @DisplayName("getPlaceDetail - 결과 없음 → IllegalStateException")
    void getPlaceDetail_empty_throws() {
        NaverSearchResponse<NaverLocalItem> empty =
                new NaverSearchResponse<>("today", 0, 1, 1, List.of());

        when(naver.searchLocal("없는곳", 5, 1, NaverLocalSortType.RANDOM))
                .thenReturn(empty);

        assertThrows(IllegalStateException.class,
                () -> placeService.getPlaceDetail("없는곳", null, null));

        verify(naver).searchLocal("없는곳", 5, 1, NaverLocalSortType.RANDOM);
        verifyNoMoreInteractions(naver, reviewMapper);
    }

    @Test
    @DisplayName("getPlaceReviews: blog/news가 비어도 예외 없이 빈 리스트 반환")
    void getPlaceReviews_emptyLists_ok() {
        String query = "역삼 카페";

        when(naver.searchBlog(query, 5, 1, com.study.trendyspot.feign.dto.NaverBlogSortType.SIM))
                .thenReturn(new NaverSearchResponse<>("today", 0, 1, 1, List.of()));
        when(naver.searchNews(query, 5, 1, com.study.trendyspot.feign.dto.NaverBlogSortType.DATE))
                .thenReturn(new NaverSearchResponse<>("today", 0, 1, 1, List.of()));

        PlaceReviewsResponseDto res = placeService.getPlaceReviews(query, 5, 5);

        assertAll(
                () -> assertEquals(query, res.query()),
                () -> assertTrue(res.reviews().isEmpty())
        );

        verify(naver).searchBlog(query, 5, 1, com.study.trendyspot.feign.dto.NaverBlogSortType.SIM);
        verify(naver).searchNews(query, 5, 1, com.study.trendyspot.feign.dto.NaverBlogSortType.DATE);
        verifyNoInteractions(reviewMapper); // 매핑 호출 없음
        verifyNoMoreInteractions(naver);
    }
}