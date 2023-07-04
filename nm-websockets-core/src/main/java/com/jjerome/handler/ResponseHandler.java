package com.jjerome.handler;

import com.jjerome.domain.PrivateGlobalData;
import com.jjerome.domain.Response;
import com.jjerome.mapper.ResponseMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;


@Component
@AllArgsConstructor
public class ResponseHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);

    private final PrivateGlobalData privateGlobalData;

    private final ExecutorService executorService;

    private final ResponseMapper responseMapper;

    public void sendJSONMessage(String sessionID, Response<?> response){

        if (!privateGlobalData.getSessions().containsKey(sessionID)){
            LOGGER.error("Send a message to an unidentified session");
            return;
        }

        try{
            privateGlobalData.getSessions().get(sessionID)
                    .sendMessage(new TextMessage(responseMapper.responseToJSON(response)));
        } catch (IOException exception){
            LOGGER.error(exception.getMessage());
        }
    }
}
