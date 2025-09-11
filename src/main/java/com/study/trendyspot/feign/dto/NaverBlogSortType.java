package com.study.trendyspot.feign.dto;

public enum NaverBlogSortType {
    SIM("sim"), DATE("date");
    private final String s;
    NaverBlogSortType(String s){
        this.s = s;
    }
    public String value(){
        return s;
    }
}
