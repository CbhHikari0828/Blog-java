package com.alex.weblog.common.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    private final static ObjectMapper INSTANCE = new ObjectMapper();

    public static String toJsonString(Object obj){
        try {
            return INSTANCE.writeValueAsString(obj);
        }catch (JacksonException e){
            return obj.toString();
        }
    }

}
