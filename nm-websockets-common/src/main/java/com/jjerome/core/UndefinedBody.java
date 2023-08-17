package com.jjerome.core;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

public class UndefinedBody extends LinkedHashMap {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // TODO: 17.08.2023  maybe can try to optimize the "convertToRealBody"
    public <R> R convertToRealBody(JavaType genericTypes) throws IllegalArgumentException {
        return OBJECT_MAPPER.convertValue(this, genericTypes);
    }
}
