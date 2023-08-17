package com.jjerome.core.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjerome.core.Request;
import com.jjerome.core.dto.RequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestMapper.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private <R, T> Object JSONTo(String json, Class<R> requestType, Class<T> bodyType){
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(requestType, bodyType);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException exception){
            LOGGER.error(exception.getMessage());
        }
        return null;
    }

    public <T> Request<T> JSONToRequest(String json, Class<T> bodyType) {
        return (Request<T>) JSONTo(json, Request.class, bodyType);
    }

    public <T> RequestDto<T> JSONToRequestDto(String json, Class<T> bodyType){
        return (RequestDto<T>) JSONTo(json, RequestDto.class, bodyType);
    }
}
