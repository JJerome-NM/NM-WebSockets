package com.jjerome.handler;

import com.jjerome.domain.Controller;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.Mapping;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.domain.Request;
import com.jjerome.domain.UndefinedBody;
import com.jjerome.exception.MappingNotFoundException;
import com.jjerome.mapper.RequestMapper;
import com.jjerome.service.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class RequestHandler {

    private final MappingsStorage mappingsStorage;

    private final ControllersStorage controllersStorage;

    private final ResponseHandler responseHandler;

    private final ExecutorService executorService;

    private final RequestMapper requestMapper;

    private final MappingService mappingService;


    public void handleMapping(Request<UndefinedBody> request) {
        if (!mappingsStorage.getMappings().containsKey(request.getPath())){
            throw new MappingNotFoundException(request.getPath() + " - mapping not found");
        }

        Mapping mapping = mappingsStorage.getMappings().get(request.getPath());

        mappingService.invokeMapping(mapping, request);


        System.out.println(mapping.getType());
    }

    public void handleMapping(WebSocketSession session, TextMessage message){
        Request<UndefinedBody> request = requestMapper.JSONToRequest(message.getPayload(), UndefinedBody.class);
        request.setSessionId(session.getId());
        handleMapping(request);
    }

    public void handleConnectMapping(){

    }

    public void handleDisconnectMapping(){

    }
}
