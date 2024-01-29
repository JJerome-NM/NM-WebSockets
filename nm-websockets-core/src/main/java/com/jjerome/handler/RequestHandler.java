package com.jjerome.handler;

import com.jjerome.core.Mapping;
import com.jjerome.core.Request;
import com.jjerome.core.RequestRepository;
import com.jjerome.core.Response;
import com.jjerome.core.UndefinedBody;
import com.jjerome.core.mapper.RequestMapper;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.exception.MappingNotFoundException;
import com.jjerome.local.data.SessionLocal;
import com.jjerome.util.InvokeUtil;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;

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

//        AntPathMatcher
    }


    public void handleMapping(Request<UndefinedBody> request) throws InvocationTargetException, IllegalAccessException {
        executorService.submit(() -> {
            String path = mappingsStorage.containsMapping(request.getPath(), true);
            if (path == null) {
                throw new MappingNotFoundException(request.getPath() + " - mapping not found");
            }

            RequestRepository.setRequest(request);

//            sessionLocal.setArgument("security.key", "SECURITY_KEY");
//            var securityKey = sessionLocal.getArgument("security.key");

            Mapping mapping = mappingsStorage.getMappingByPath(path);
            mapping.applyRequestFieldsCollectFunctions(request);
            mapping.applyInvokeFunction(request);

            String[] allPathVariableNames = mapping.getPathVariablesNames();

            if (allPathVariableNames.length > 0) {
                Matcher matcher = mapping.getRegexPathPattern().matcher(request.getPath());

                if (matcher.find()) {
                    for (int i = 1; i <= matcher.groupCount(); ++i) {
                        String value = matcher.group(i);
                        request.getPathVariables().put(allPathVariableNames[i - 1], value);
                    }
                }
            }

            // Todo Enum for mapping types mb tray use factory or something else

            invokeMapping(mapping, request);
        });
    }

    public void handleMapping(WebSocketSession session, TextMessage message){
        Request<UndefinedBody> request = requestMapper.JSONToRequest(message.getPayload(), UndefinedBody.class);
        request.setSessionId(session.getId());
        request.setHttpHeaders(session.getHandshakeHeaders());
        try {
            handleMapping(request);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleConnectMapping(WebSocketSession session){
        mappingsStorage.getConnectMappings().forEach((mapping -> {
            Request<UndefinedBody> request = new Request<>(session.getId());

            RequestRepository.setRequest(request);

            invokeMapping(mapping, request);
        }));
    }

    public void handleDisconnectMapping(){

    }

    private void invokeMapping(Mapping mapping, Request<UndefinedBody> request) {
        if (mapping.returnsResponse()){
            try {
                Object response = invokeUtil.invoke(mapping);

                if (!mapping.getComponentAnnotation().disableReturnResponse()){
                    String responsePath = mapping.getController().buildFullResponsePath(mapping);

                    responseHandler.sendJSONMessage(request.getSessionId(), new Response<>(responsePath, response));
                }
            } catch (RuntimeException e){

                System.out.println(e);
            }
        } else {
            invokeUtil.invoke(mapping);
        }
    }
}
