package com.jjerome.handler;

import com.jjerome.core.Controller;
import com.jjerome.core.Mapping;
import com.jjerome.domain.PrivateGlobalData;
import com.jjerome.predefined.AvailableMappingDetails;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
    private final RequestHandler requestHandler;
    private final PrivateGlobalData privateGlobalData;
    private final String handlerPath;
    @Value("${nm-websocket.allowed-origins:*}")
    private String[] allowedOrigins = {};

    public WebSocketHandler(RequestHandler requestHandler,
                            PrivateGlobalData privateGlobalData,
                            String handlerPath) {
        this.requestHandler = requestHandler;
        this.privateGlobalData = privateGlobalData;
        this.handlerPath = handlerPath;

        LOGGER.info("Handler with path '%s' is registered.".formatted(handlerPath));
    }


    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) {
        requestHandler.handleMapping(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println(session.getId());

        privateGlobalData.addSession(session);
        requestHandler.handleConnectMapping(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println(session.getId());

        privateGlobalData.removeSession(session);
        requestHandler.handleDisconnectMapping(session);
    }

    public String getHandlerPath() {
        return handlerPath;
    }

    public List<Controller> getAvailableControllers() {
        return requestHandler.getMappingsStorage().getControllers();
    }

    public List<AvailableMappingDetails> getAvailableMappingDetails() {
        List<AvailableMappingDetails> details = new ArrayList<>();
        for (Mapping m : requestHandler.getMappingsStorage().getMappings()) {
            details.add(new AvailableMappingDetails(m.buildFullPath(), m.getRegexPathPattern().pattern(), m.getID()));
        }
        return details;
    }
}
