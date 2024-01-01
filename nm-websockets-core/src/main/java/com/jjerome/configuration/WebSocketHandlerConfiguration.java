package com.jjerome.configuration;

import com.jjerome.domain.DomainStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

public class WebSocketHandlerConfiguration implements WebSocketConfigurer {

    @Autowired
    private DomainStorage domainStorage;

    @Value("${nm-websocket.allowed-origins:*}")
    private String[] allowedOrigins;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        domainStorage.getHandlers().forEach((path, handler) ->
                registry.addHandler(handler, path).setAllowedOrigins(allowedOrigins));
    }
}
