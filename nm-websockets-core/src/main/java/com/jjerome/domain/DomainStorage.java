package com.jjerome.domain;

import com.jjerome.exception.HandlerNotFoundException;
import com.jjerome.handler.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DomainStorage {

    private Map<String, WebSocketHandler> handlersByControllerClassName;
    private Map<String, WebSocketHandler> handlers;
    private final PrivateGlobalData privateData;

    public DomainStorage(PrivateGlobalData privateGlobalData) {
        this.privateData = privateGlobalData;
        this.handlers = new HashMap<>();
    }

    public WebSocketHandler getMyHandler() {
        WebSocketSession session = privateData.getSession()
                .orElseThrow(() -> new HandlerNotFoundException("No session found"));

        return handlers.get(Objects.requireNonNull(session.getUri()).getPath());
    }

    protected WebSocketHandler getMyHandler(String controllerClassName) {
        return Optional.ofNullable(handlersByControllerClassName.get(controllerClassName))
                .orElseThrow(() -> new HandlerNotFoundException("No handler found for class " + controllerClassName));
    }

    public void setHandlers(List<WebSocketHandler> handlers) {
        setHandlers(handlers.stream().collect(Collectors.toMap(WebSocketHandler::getHandlerPath, Function.identity())));
    }

    public void setHandlers(Map<String, WebSocketHandler> handlers) {
        this.handlers = handlers;

        this.handlersByControllerClassName = this.handlers.values().stream()
                .flatMap(handler -> handler.getAvailableControllers().stream()
                        .map(controller -> Map.entry(controller.getClazz().getName(), handler)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing));
    }

    public WebSocketHandler getHandler(String path) {
        return Optional.ofNullable(handlers.get(path)).orElseThrow(
                () -> new HandlerNotFoundException("Handler with path '%s' is not found.".formatted(path)));
    }

    public Map<String, WebSocketHandler> getHandlers() {
        return handlers;
    }
}
