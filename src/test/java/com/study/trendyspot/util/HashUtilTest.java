package com.study.trendyspot.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class HashUtilTest {
    @Test
    @DisplayName("normalize: 앞뒤 공백 제거, 공백 1칸, 소문자 변환")
    void normalize_basic(){
        String raw = "  스타벅스   강남역점  ";
        String norm = HashUtil.normalize(raw);
        assertThat(norm).isEqualTo("스타벅스 강남역점");
    }

    @ParameterizedTest(name = "[{index}] 변형 입력 동일 해시일 것: \"{0}\"")
    @ValueSource(strings = {
            "스타벅스 강남역점",
            "  스타벅스   강남역점 ",
            "\t스타벅스    강남역점\t",
            "스타벅스  강남역점",
            "스타벅스 강남역점  "
    })
    @DisplayName("hashPlaceName: 공백,탭,여러칸 변형되어도 동일 해시 반환")
    void hashPlaceName_variants_sameHash(String input){
        String base = "스타벅스 강남역점";
        String expected = HashUtil.hashPlaceName(base);
        String actual = HashUtil.hashPlaceName(input);
        assertThat(actual).isEqualTo(expected);
        assertThat(actual).hasSize(64);
    }

    @Test
    @DisplayName("sha256Hex 테스트, abc일 때")
    void sha256_standard(){
        String expected = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
        String actual = HashUtil.sha256Hex("abc");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("normalize 대소문자 -> 소문자로 정규화")
    void normalize_caseFold(){
        String raw = " Cafe LATTE ";
        String norm = HashUtil.normalize(raw);
        assertThat(norm).isEqualTo("cafe latte");
    }
}
