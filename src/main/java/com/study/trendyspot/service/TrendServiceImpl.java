package com.study.trendyspot.service;

import com.study.trendyspot.dto.TrendQueryRequest;
import com.study.trendyspot.dto.TrendResponseDto;
import com.study.trendyspot.feign.NaverDataLabHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class TrendServiceImpl implements TrendService{
    private final NaverDataLabHelper dataLabHelper;

    @Override
    public TrendResponseDto getTrends(TrendQueryRequest request){
        var res = dataLabHelper.searchTrends(
                request.keyword(), request.from(), request.to(), request.granularity(), request.gender(),request.ages()
        );

        var from = LocalDate.parse(request.from());
        var to = LocalDate.parse(request.to());

        var points = new ArrayList<TrendResponseDto.Point>();
        res.results().forEach(r ->
                r.data().forEach(d -> {var day = LocalDate.parse(d.period());
                                        if(!day.isBefore(from) && !day.isAfter(to)){
                                            points.add(new TrendResponseDto.Point(day.toString(),d.ratio()));
                                        }
                })
        );
        points.sort(Comparator.comparing(TrendResponseDto.Point::date));
        return new TrendResponseDto(request.keyword(), request.granularity().name(), points);
    }

}
