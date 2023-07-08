package com.jjerome.handler;

import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.domain.PrivateGlobalData;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

    private final RequestHandler requestHandler;

    private final ResponseHandler responseHandler;

    private final MappingsStorage mappingsStorage;

    private final ControllersStorage controllersStorage;

    private final PrivateGlobalData privateGlobalData;

    public WebSocketHandler(RequestHandler requestHandler,
                            ResponseHandler responseHandler,
                            MappingsStorage mappingsStorage,
                            ControllersStorage controllersStorage,
                            PrivateGlobalData privateGlobalData){
        this.requestHandler = requestHandler;
        this.responseHandler = responseHandler;
        this.mappingsStorage = mappingsStorage;
        this.controllersStorage = controllersStorage;
        this.privateGlobalData = privateGlobalData;

        LOGGER.info("NM-WebSockets successfully started");
    }


    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception {
        requestHandler.handleMapping(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        System.out.println(session.getId());

        privateGlobalData.getSessions().put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println(session.getId());
    }
}
