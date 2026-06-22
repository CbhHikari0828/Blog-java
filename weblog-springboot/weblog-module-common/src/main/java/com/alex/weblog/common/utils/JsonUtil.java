package com.alex.weblog.common.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    private final static ObjectMapper INSTANCE = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            ;

    public static String toJsonString(Object obj){
        try {
            return INSTANCE.writeValueAsString(obj);
        }catch (JacksonException e){
            return obj.toString();
        }
    }

}
