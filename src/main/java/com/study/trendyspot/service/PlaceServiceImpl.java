package com.study.trendyspot.service;

import com.study.trendyspot.dto.PlaceDetailResponseDto;
import com.study.trendyspot.dto.PlaceReviewsResponseDto;
import com.study.trendyspot.dto.ReviewItemDto;
import com.study.trendyspot.feign.NaverSearchHelper;
import com.study.trendyspot.feign.dto.NaverBlogSortType;
import com.study.trendyspot.feign.dto.NaverLocalSortType;
import com.study.trendyspot.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

import static com.study.trendyspot.util.TextNetUtils.*;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService{

    private final NaverSearchHelper naver;
    private final ReviewMapper reviewMapper;
    @Override
    public PlaceDetailResponseDto getPlaceDetail(String query, Double latitude, Double longitude) {

        var res = naver.searchLocal(query, 5, 1, NaverLocalSortType.RANDOM);
        var items = res.items();
        if (CollectionUtils.isEmpty(items)) {
            throw new IllegalStateException("장소를 찾을 수 없습니다.");
        }
        var i = res.items().getFirst();

        return new PlaceDetailResponseDto(
                stripHtml(i.title()),
                i.category(),
                i.telephone(),
                i.address(),
                i.roadAddress(),
                parseDoubleOrNull(i.mapx()),
                parseDoubleOrNull(i.mapy()),
                i.link()
        );
    }
    @Override
    public PlaceReviewsResponseDto getPlaceReviews(String query, int blogN, int newsN) {

        var reviews = new ArrayList<ReviewItemDto>();

        var blogs = naver.searchBlog(query, blogN, 1, NaverBlogSortType.SIM);
        if (!CollectionUtils.isEmpty(blogs.items())) {
            blogs.items().stream().map(reviewMapper::fromBlog).forEach(reviews::add);
        }

        var news = naver.searchNews(query, newsN, 1, NaverBlogSortType.DATE);
        if (!CollectionUtils.isEmpty(news.items())) {
            news.items().stream().map(reviewMapper::fromNews).forEach(reviews::add);
        }

        return new PlaceReviewsResponseDto(query, reviews);
    }
}
