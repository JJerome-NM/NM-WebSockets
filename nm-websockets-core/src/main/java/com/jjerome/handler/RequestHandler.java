package com.jjerome.handler;

import com.jjerome.domain.Controller;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.Mapping;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.domain.Request;
import com.jjerome.domain.Response;
import com.jjerome.domain.UndefinedBody;
import com.jjerome.exception.MappingNotFoundException;
import com.jjerome.mapper.RequestMapper;
import com.jjerome.service.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

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
        Controller controller = controllersStorage.getController(mapping.getControllerClazz());

        Object response = mappingService.invokeMapping(mapping, request);

        if (mapping.getMethodReturnType() != null && !mapping.getMappingAnnotation().disableReturnResponse()){
            String responsePath = controller.getControllerAnnotation().responsePathPrefix()
                    + mapping.getMappingAnnotation().responsePath();

            responseHandler.sendJSONMessage(request.getSessionId(), new Response<>(responsePath, response));
        }
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
