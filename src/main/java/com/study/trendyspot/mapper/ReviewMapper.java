package com.study.trendyspot.mapper;

import com.study.trendyspot.dto.ReviewItemDto;
import com.study.trendyspot.dto.ReviewType;
import com.study.trendyspot.feign.dto.NaverBlogItem;
import com.study.trendyspot.feign.dto.NaverNewsItem;
import org.springframework.stereotype.Component;
import java.net.URI;

import static com.study.trendyspot.util.TextNetUtils.*;

@Component
public class ReviewMapper {

    public ReviewItemDto fromBlog(NaverBlogItem b){
        return new ReviewItemDto(
                ReviewType.BLOG,
                stripHtml(b.title()),
                stripHtml(b.description()),
                b.bloggername(),
                b.link()
        );
    }

    public ReviewItemDto fromNews(NaverNewsItem n){
        return new ReviewItemDto(
                ReviewType.NEWS,
                stripHtml(n.title()),
                stripHtml(n.description()),
                safeSource(extractHost(n.link()), n.originallink()),
                n.link() != null ? n.link() : n.originallink()
        );
    }
}
