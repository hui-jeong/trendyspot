package com.study.trendyspot.feign.dto;

//public record NaverParams(String query, Integer display, Integer start, String sort) {
//}
public class NaverParams {
    private String query;
    private Integer display;
    private Integer start;
    private String sort;

    public NaverParams(String query, Integer display, Integer start, String sort) {
        this.query = query;
        this.display = display;
        this.start = start;
        this.sort = sort;
    }

    public String getQuery()   { return query; }
    public Integer getDisplay(){ return display; }
    public Integer getStart()  { return start; }
    public String getSort()    { return sort; }
}
