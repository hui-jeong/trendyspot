package com.study.trendyspot.service;

import com.study.trendyspot.dto.PlaceDetailResponseDto;
import com.study.trendyspot.dto.PlaceReviewsResponseDto;
import com.study.trendyspot.dto.ReviewItemDto;
import com.study.trendyspot.feign.NaverSearchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;

import static com.study.trendyspot.util.TextNetUtils.*;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService{

    private final NaverSearchClient client;

    @Override
    public PlaceDetailResponseDto getPlaceDetail(String query, Double latitude, Double longitude) {

        var res = client.searchLocal(query, 5, 1, "sim");

        if (res.items() == null || res.items().isEmpty()) {
            throw new IllegalStateException("장소를 찾을 수 없습니다.");
        }
        var i = res.items().get(0);

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

        var blogs = client.searchBlog(query, blogN, 1, "sim");
        if (blogs.items() != null) {
            blogs.items().forEach(b -> reviews.add(new ReviewItemDto(
                    "blog",
                    stripHtml(b.title()),
                    stripHtml(b.description()),
                    b.bloggername(),
                    b.link()
            )));
        }

        var news = client.searchNews(query, newsN, 1, "date");
        if (news.items() != null) {
            news.items().forEach(n -> reviews.add(new ReviewItemDto(
                    "news",
                    stripHtml(n.title()),
                    stripHtml(n.description()),
                    // 링크가 없을 수도 있으니 originallink 보강
                    safeSource(extractHost(n.link()), n.originallink()),
                    n.link() != null ? n.link() : n.originallink()
            )));
        }

        return new PlaceReviewsResponseDto(query, reviews);
    }

    private static String safeSource(String hostFromLink, String fallbackUrl) {

        if (hostFromLink != null) return hostFromLink;
        try { return fallbackUrl == null ? null : new URI(fallbackUrl).getHost(); }
        catch (Exception e) { return null; }
    }
}
