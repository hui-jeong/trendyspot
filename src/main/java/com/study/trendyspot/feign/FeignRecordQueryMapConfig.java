package com.study.trendyspot.feign;

import feign.QueryMapEncoder;
import feign.querymap.BeanQueryMapEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
@Configuration
public class FeignRecordQueryMapConfig {
    private static final QueryMapEncoder BEAN = new BeanQueryMapEncoder();
    @Bean
    public feign.QueryMapEncoder queryMapEncoder(){
        return obj->{
            if (obj == null) return java.util.Collections.emptyMap();

            Class<?> type = obj.getClass();
            if (!type.isRecord()) return BEAN.encode(obj);

            Map<String, Object> map = new LinkedHashMap<>();
            for (var rc : type.getRecordComponents()) {
                Object v = null;
                try {
                    v = rc.getAccessor().invoke(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                if (v != null) map.put(rc.getName(), v);
            }
            return map;
        };
    }
}
