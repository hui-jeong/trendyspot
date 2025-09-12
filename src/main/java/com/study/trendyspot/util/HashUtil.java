package com.study.trendyspot.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class HashUtil {

    public static String normalize(String raw){
        return raw.trim().replaceAll("\\s+"," ").toLowerCase();
    }

    public static String sha256Hex(String s){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(s.getBytes(StandardCharsets.UTF_8)));
        }catch (Exception e){
            throw new IllegalStateException("SHA-256 오류 ", e);
        }
    }

    public static String hashPlaceName(String raw) {
        return sha256Hex(normalize(raw));
    }
}
