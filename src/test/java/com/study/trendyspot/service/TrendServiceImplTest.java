package com.study.trendyspot.service;

import com.study.trendyspot.dto.TrendQueryRequest;
import com.study.trendyspot.dto.TrendResponseDto;
import com.study.trendyspot.entity.Granularity;
import com.study.trendyspot.feign.NaverDataLabHelper;
import com.study.trendyspot.feign.dto.NaverDataLabResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class TrendServiceImplTest {
    @Mock
    NaverDataLabHelper naverDataLabHelper;

    @InjectMocks
    TrendServiceImpl trendService;

    @Test
    @DisplayName("helper 응답 매핑하고 trendResponseDto로 반환")
    void getTrend_ok(){
        var data = List.of(
                new NaverDataLabResponse.Result.Data("2025-08-01",70.0),
                new NaverDataLabResponse.Result.Data("2025-08-02",72.0)
        );
        var result = new NaverDataLabResponse.Result("강남 카페",List.of("강남 카페"),data);
        var feignResponse= new NaverDataLabResponse("2025-08-01","2025-08-31","date",List.of(result));

        given(naverDataLabHelper.searchTrends(
                anyString(), anyString(), anyString(), any(Granularity.class),
                any(), anyList()
        )).willReturn(feignResponse);

        var request = new TrendQueryRequest(
                "강남 카페", Granularity.DAILY, "2025-08-01", "2025-08-31",
                "f", Collections.singletonList("2")
        );

        TrendResponseDto response = trendService.getTrends(request);

        assertEquals("강남 카페", response.keyword());
        assertEquals("DAILY", response.granularity());
        assertNotNull(response.points());
        assertEquals(2, response.points().size());
        assertEquals("2025-08-01", response.points().getFirst().date());
        assertEquals(70.0, response.points().getFirst().index(), 1e-9);

    }

    @Test
    @DisplayName("날짜 포맷 잘못되면 DateTimeParseException")
    void getTrends_badDate_throws() {
        var req = new TrendQueryRequest(
                "강남 카페", Granularity.DAILY,
                "BAD-DATE", "2025-08-31",
                "f", Collections.singletonList("2")
        );
        assertThrows(DateTimeParseException.class, () -> trendService.getTrends(req));
    }

    @Test
    @DisplayName("요청 범위 밖의 데이터는 필터링")
    void getTrends_outOfRange_filtered() {
        // 7/31은 범위 밖, 8/01만 포함되어야 함
        var data = List.of(
                new NaverDataLabResponse.Result.Data("2025-07-31", 66.0),
                new NaverDataLabResponse.Result.Data("2025-08-01", 70.0)
        );
        var result = new NaverDataLabResponse.Result("강남 카페", List.of("강남 카페"), data);
        var resp = new NaverDataLabResponse("2025-07-01", "2025-08-31", "date", List.of(result));

        given(naverDataLabHelper.searchTrends(anyString(), anyString(), anyString(),
                any(Granularity.class), any(), anyList()))
                .willReturn(resp);

        var req = new TrendQueryRequest(
                "강남 카페", Granularity.DAILY,
                "2025-08-01", "2025-08-31",
                "f", Collections.singletonList("2")
        );

        TrendResponseDto dto = trendService.getTrends(req);
        assertEquals(1, dto.points().size());
        assertEquals("2025-08-01", dto.points().getFirst().date());
        assertEquals(70.0, dto.points().getFirst().index(), 1e-9);
    }

}
