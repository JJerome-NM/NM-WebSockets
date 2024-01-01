package com.jjerome.domain;

import com.jjerome.exception.HandlerNotFoundException;
import com.jjerome.handler.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DomainStorage {

    private final Map<String, WebSocketHandler> handlers;

    public DomainStorage() {
        this.handlers = new HashMap<>();
    }

    public WebSocketHandler getHandler(String path) {
        return Optional.ofNullable(handlers.get(path)).orElseThrow(
                () -> new HandlerNotFoundException("Handler with name '%s' is not found.".formatted(path)));
    }

    public Map<String, WebSocketHandler> getHandlers() {
        return handlers;
    }
}
