package com.jjerome.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjerome.domain.Request;
import com.jjerome.dto.RequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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


//    public static void main(String[] args) throws JsonProcessingException {
//        RequestMapper requestMapper = new RequestMapper();
//
//        String json = "{\n" +
//                "    \"path\": \"test\",\n" +
//                "    \"body\": {\n" +
//                "        \"name\": \"JJerome\",\n" +
//                "        \"age\": 12" +
//                "    }\n" +
//                "}";
//
//        Request<User> request = requestMapper.JSONToRequest(json, User.class);
//
//        System.out.println(request.getPath());
//        System.out.println(request.getBody());
//    }
}
