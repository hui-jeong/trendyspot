package com.study.trendyspot.feign.dto;

public enum NaverLocalSortType {
    RANDOM("random"), COMMENT("comment");
    private final String v;
    NaverLocalSortType(String v){ this.v = v; }
    public String value(){ return v; }
}
