package com.jjerome.predefined;

import com.jjerome.domain.DomainStorage;
import com.jjerome.handler.WebSocketHandler;
import com.jjerome.reflection.context.annotation.WSController;
import com.jjerome.reflection.context.anotation.WSConnectMapping;

import java.util.Map;
import java.util.UUID;

@WSController(responsePathPrefix = "/predefined", handlerPath = "*")
public class PredefinedConnectMappings {

    private final DomainStorage domainStorage;

    public PredefinedConnectMappings(DomainStorage domainStorage) {
        this.domainStorage = domainStorage;
    }

    @WSConnectMapping
    public Map<String, UUID> connect() {
        WebSocketHandler handler = domainStorage.getMyHandler();

        return handler.getMappingsUUIDs();
    }
}
