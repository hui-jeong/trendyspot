package com.study.trendyspot.feign;

import com.study.trendyspot.entity.Granularity;
import com.study.trendyspot.feign.dto.NaverDataLabRequest;
import com.study.trendyspot.feign.dto.NaverDataLabResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NaverDataLabHelper {
    private final NaverDataLabClient client;

    public NaverDataLabResponse searchTrends(
            String keyword,
            String from,
            String to,
            Granularity granularity,
            String gender,
            List<String> ages
    ){
        String timeUnit = (granularity == granularity.DAILY) ? "date" : "week";

        var body = new NaverDataLabRequest(
                from, to, timeUnit, List.of(new NaverDataLabRequest.KeywordGroup(keyword,List.of(keyword))),gender,ages
        );
        return client.searchTrends(body);
    }


}
