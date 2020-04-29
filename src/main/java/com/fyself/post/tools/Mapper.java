package com.fyself.post.tools;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collections;
import java.util.Map;

import static com.fyself.seedwork.util.JsonUtil.MAPPER;

public class Mapper {
    public static Map toMap(Object o){
        try {
            return MAPPER.readValue(MAPPER.writeValueAsString(o), Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }
}
