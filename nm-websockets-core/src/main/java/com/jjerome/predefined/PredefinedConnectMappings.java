package com.jjerome.predefined;

import com.jjerome.domain.DomainStorage;
import com.jjerome.handler.WebSocketHandler;
import com.jjerome.reflection.context.annotation.WSController;
import com.jjerome.reflection.context.anotation.WSConnectMapping;

import java.util.List;

@WSController(responsePathPrefix = "/predefined", handlerPath = "*")
public class PredefinedConnectMappings {

    private final DomainStorage domainStorage;

    public PredefinedConnectMappings(DomainStorage domainStorage) {
        this.domainStorage = domainStorage;
    }

    @WSConnectMapping(responsePath = "/available_mapping")
    public List<AvailableMappingDetails> connect() {

        WebSocketHandler handler = domainStorage.getMyHandler();

        return handler.getAvailableMappingDetails();
    }
}
