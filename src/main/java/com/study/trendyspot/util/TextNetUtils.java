package com.study.trendyspot.util;

import java.net.URI;
import java.util.regex.Pattern;

public class TextNetUtils {
    private TextNetUtils(){}

    private static final Pattern HTML_TAG = Pattern.compile("<[^>]+>");

    //html 태크 제거
    public static String stripHtml(String s){
        if(s == null){
            return null;
        }
        return HTML_TAG.matcher(s).replaceAll("");
    }

    public static Double parseDoubleOrNull(String s){
        if(s == null || s.isBlank()){
            return null;
        }
        try{
            return Double.parseDouble(s);
        }catch (NumberFormatException e){
            return null;
        }
    }

    // URL host 추출
    public static String extractHost(String url) {
        if (url == null || url.isBlank()) return null;
        try {
            return new URI(url).getHost();
        } catch (Exception e) {
            return null;
        }
    }
}
