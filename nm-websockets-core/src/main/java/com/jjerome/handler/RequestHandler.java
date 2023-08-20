package com.jjerome.handler;

import com.jjerome.core.Mapping;
import com.jjerome.core.RequestRepository;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.core.Request;
import com.jjerome.core.Response;
import com.jjerome.core.UndefinedBody;
import com.jjerome.exception.MappingNotFoundException;
import com.jjerome.core.mapper.RequestMapper;
import com.jjerome.util.InvokeUtil;
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

    public RequestHandler(MappingsStorage mappingsStorage, ResponseHandler responseHandler,
                          ExecutorService executorService, RequestMapper requestMapper, InvokeUtil invokeUtil) {
        this.mappingsStorage = mappingsStorage;
        this.responseHandler = responseHandler;
        this.executorService = executorService;
        this.requestMapper = requestMapper;
        this.invokeUtil = invokeUtil;
    }


    public void handleMapping(Request<UndefinedBody> request) throws InvocationTargetException, IllegalAccessException {
        executorService.submit(() -> {
            double start = System.nanoTime();

            if (!mappingsStorage.containsMapping(request.getPath())){
                throw new MappingNotFoundException(request.getPath() + " - mapping not found");
            }

            RequestRepository.setRequest(request);

            System.out.println(request.getPath());

            Mapping mapping = mappingsStorage.getMappingByPath(request.getPath());

            Object response = invokeUtil.invoke(mapping);

            if (mapping.getMethodReturnType() != null && !mapping.getComponentAnnotation().disableReturnResponse()){
                String responsePath = mapping.getController().buildFullPath(mapping);

                responseHandler.sendJSONMessage(request.getSessionId(), new Response<>(responsePath, response));
            }

            System.out.println("Request runtime = " + (System.nanoTime() - start));
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
