package com.jjerome.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

public class UndefinedBody extends LinkedHashMap {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public <R> R convertToRealBody(Class<R> bodyType) throws IllegalArgumentException{
        return OBJECT_MAPPER.convertValue(this, bodyType);
    }
}
