package com.study.trendyspot.dto;

public enum ReviewType {
    BLOG("blog"),NEWS("news");

    private final String s;

    ReviewType(String s){
        this.s = s;
    }
    public String value(){
        return s;
    }
}