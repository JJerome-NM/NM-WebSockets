package com.jjerome.handler;

import com.jjerome.core.Mapping;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.core.Request;
import com.jjerome.core.Response;
import com.jjerome.core.UndefinedBody;
import com.jjerome.exception.MappingNotFoundException;
import com.jjerome.core.mapper.RequestMapper;
import com.jjerome.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class RequestHandler {

    private final MappingsStorage mappingsStorage;

    private final ResponseHandler responseHandler;

    private final ExecutorService executorService;

    private final RequestMapper requestMapper;

    private final MappingUtil mappingUtil;


    public void handleMapping(Request<UndefinedBody> request) throws InvocationTargetException, IllegalAccessException {
        if (!mappingsStorage.getMappings().containsKey(request.getPath())){
            throw new MappingNotFoundException(request.getPath() + " - mapping not found");
        }

        Mapping mapping = mappingsStorage.getMappings().get(request.getPath());

        Object response = mappingUtil.invokeMapping(mapping, request);

        if (mapping.getMethodReturnType() != null && !mapping.getMappingAnnotation().disableReturnResponse()){
            String responsePath = mapping.getController().getControllerAnnotation().responsePathPrefix()
                    + mapping.getMappingAnnotation().responsePath();

            responseHandler.sendJSONMessage(request.getSessionId(), new Response<>(responsePath, response));
        }
    }

    public void handleMapping(WebSocketSession session, TextMessage message){
        Request<UndefinedBody> request = requestMapper.JSONToRequest(message.getPayload(), UndefinedBody.class);
        request.setSessionId(session.getId());
        try {
            handleMapping(request);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleConnectMapping(){

    }

    public void handleDisconnectMapping(){

    }
}
