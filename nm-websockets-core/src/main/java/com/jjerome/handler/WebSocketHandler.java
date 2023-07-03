package com.jjerome.handler;

import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.MappingsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

    private final RequestHandler requestHandler;

    private final ResponseHandler responseHandler;

    private final MappingsStorage mappingsStorage;

    private final ControllersStorage controllersStorage;


    public WebSocketHandler(RequestHandler requestHandler,
                            ResponseHandler responseHandler,
                            MappingsStorage mappingsStorage,
                            ControllersStorage controllersStorage){
        this.requestHandler = requestHandler;
        this.responseHandler = responseHandler;
        this.mappingsStorage = mappingsStorage;
        this.controllersStorage = controllersStorage;

        LOGGER.info("WebSockets successfully started");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        System.out.println(message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        System.out.println(session.getId());

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println(session.getId());
    }
}
