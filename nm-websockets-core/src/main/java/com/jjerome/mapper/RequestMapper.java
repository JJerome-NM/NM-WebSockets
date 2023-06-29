package com.jjerome.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjerome.domain.Request;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestMapper.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> Request<T> JSONToRequest(String json, Class<T> bodyType) {
        try {
            JavaType requestType = objectMapper.getTypeFactory().constructParametricType(Request.class, bodyType);
            return objectMapper.readValue(json, requestType);
        } catch (JsonProcessingException exception){
            LOGGER.error(exception.getMessage());
        }
        return null;
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
