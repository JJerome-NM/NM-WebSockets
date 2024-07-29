package com.jjerome.handler;

import com.jjerome.core.Response;
import com.jjerome.core.mapper.ResponseMapper;
import com.jjerome.domain.PrivateGlobalData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

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

    public void sendJSONMessage(String sessionID, Response<?> response) {

//        if (!privateGlobalData.containsSession(sessionID)){
//            LOGGER.error("Send a message to an unidentified session");
//            return;
//        }

        try {
            WebSocketSession session = privateGlobalData.getSession(sessionID)
                    .orElseThrow(() -> new IllegalStateException("Send a message to an unidentified session"));

            System.out.println(response.getPath());

            session.sendMessage(responseMapper.buildTextMessage(response));
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
        }
    }
}
