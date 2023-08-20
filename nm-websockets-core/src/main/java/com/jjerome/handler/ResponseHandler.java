package com.jjerome.handler;

import com.jjerome.domain.PrivateGlobalData;
import com.jjerome.core.Response;
import com.jjerome.core.mapper.ResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutorService;


@Component
public class ResponseHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);

    private final PrivateGlobalData privateGlobalData;

    private final ExecutorService executorService;

    private final ResponseMapper responseMapper;

    public ResponseHandler(PrivateGlobalData privateGlobalData, ExecutorService executorService,
                           ResponseMapper responseMapper) {
        this.privateGlobalData = privateGlobalData;
        this.executorService = executorService;
        this.responseMapper = responseMapper;
    }

    public void sendJSONMessage(String sessionID, Response<?> response){

        if (!privateGlobalData.getSessions().containsKey(sessionID)){
            LOGGER.error("Send a message to an unidentified session");
            return;
        }

        try{
            privateGlobalData.getSessions().get(sessionID).sendMessage(responseMapper.buildTextMessage(response));
        } catch (IOException exception){
            LOGGER.error(exception.getMessage());
        }
    }
}
