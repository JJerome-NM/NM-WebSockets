package com.jjerome.handler;

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

    private final PrivateGlobalData privateGlobalData;

    public WebSocketHandler(RequestHandler requestHandler,
                            PrivateGlobalData privateGlobalData) {
        this.requestHandler = requestHandler;
        this.privateGlobalData = privateGlobalData;

        LOGGER.info("NM-WebSockets successfully started");
        LOGGER.error("Happy hacking😘");
    }


    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) {
//        double start = System.nanoTime();
        requestHandler.handleMapping(session, message);
//        System.out.println("Request runtime = " + (System.nanoTime() - start));

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println(session.getId());

        privateGlobalData.getSessions().put(session.getId(), session);
        requestHandler.handleConnectMapping();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println(session.getId());

        requestHandler.handleDisconnectMapping();
    }
}
