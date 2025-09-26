package com.study.trendyspot.feign.dto;

import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.util.List;

public record NaverDataLabResponse(
        String startDate,
        String endDate,
        String timeUnit,
        List<Result> results
) {
    public record Result(
            String title,
            List<String> keywords,
            List<Data> data
    ){
        public record Data(String period, double ratio){}
    }
}
