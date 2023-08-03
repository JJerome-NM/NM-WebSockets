package com.jjerome.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjerome.domain.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseMapper.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    public String responseToJSON(Response<?> response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException exception){
            LOGGER.error(exception.getMessage());
        }
        return null;
    }
}
