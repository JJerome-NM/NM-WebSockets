package com.jjerome.core.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjerome.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
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

    public TextMessage buildTextMessage(Response<?> response){
        return new TextMessage(responseToJSON(response));
    }
}
