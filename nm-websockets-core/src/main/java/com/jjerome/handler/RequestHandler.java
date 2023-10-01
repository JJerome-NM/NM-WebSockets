package com.jjerome.handler;

import com.jjerome.core.Mapping;
import com.jjerome.core.RequestRepository;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.core.Request;
import com.jjerome.core.Response;
import com.jjerome.core.UndefinedBody;
import com.jjerome.exception.MappingNotFoundException;
import com.jjerome.core.mapper.RequestMapper;
import com.jjerome.local.data.SessionLocal;
import com.jjerome.util.InvokeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;

@Component
public class RequestHandler {

    private final MappingsStorage mappingsStorage;

    private final ResponseHandler responseHandler;

    private final ExecutorService executorService;

    private final RequestMapper requestMapper;

    private final InvokeUtil invokeUtil;

    private final SessionLocal sessionLocal;

    public RequestHandler(MappingsStorage mappingsStorage, ResponseHandler responseHandler,
                          ExecutorService executorService, RequestMapper requestMapper, InvokeUtil invokeUtil,
                          SessionLocal sessionLocal) {
        this.mappingsStorage = mappingsStorage;
        this.responseHandler = responseHandler;
        this.executorService = executorService;
        this.requestMapper = requestMapper;
        this.invokeUtil = invokeUtil;
        this.sessionLocal = sessionLocal;
    }


    public void handleMapping(Request<UndefinedBody> request) throws InvocationTargetException, IllegalAccessException {
        executorService.submit(() -> {
            if (!mappingsStorage.containsMapping(request.getPath())){
                throw new MappingNotFoundException(request.getPath() + " - mapping not found");
            }

            RequestRepository.setRequest(request);

//            sessionLocal.setArgument("security.key", "SECURITY_KEY");
//            var securityKey = sessionLocal.getArgument("security.key");

            Mapping mapping = mappingsStorage.getMappingByPath(request.getPath());

            if (mapping.returnsResponse()){
                Object response = invokeUtil.invoke(mapping);

                if (mapping.getMethodReturnType() != null && !mapping.getComponentAnnotation().disableReturnResponse()){
                    String responsePath = mapping.getController().buildFullPath(mapping);

                    responseHandler.sendJSONMessage(request.getSessionId(), new Response<>(responsePath, response));
                }
            } else {
                invokeUtil.invoke(mapping);
            }
        });
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
