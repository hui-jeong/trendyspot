package com.study.trendyspot.feign.dto;

import java.util.List;
public record NaverDataLabRequest(
        String startDate,
        String endDate,
        String timeUnit,
        List<KeywordGroup> keywordGroups,
        String gender,
        List<String> ages
) {
    public record KeywordGroup(String groupName, List<String> keywords) {}
}
