package com.jjerome.configuration;

import com.jjerome.handler.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

public class WebSocketHandlerConfiguration implements WebSocketConfigurer {

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Value("${nmwebsocket.path:/socket}")
    private String path;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, path);
    }
}
