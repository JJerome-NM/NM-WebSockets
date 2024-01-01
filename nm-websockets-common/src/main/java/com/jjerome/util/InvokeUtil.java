package com.jjerome.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjerome.core.Invocable;
import com.jjerome.core.Mapping;
import com.jjerome.core.Request;
import com.jjerome.core.RequestRepository;
import com.jjerome.core.UndefinedBody;
import com.jjerome.reflection.context.AnnotatedParameter;
import com.jjerome.reflection.context.annotation.WSPathVariable;
import com.jjerome.reflection.context.annotation.WSRequestBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class InvokeUtil implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvokeUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String BAD_REQUEST_BODY = "%s wants to request %s but passes the wrong request body";
    
    private static InvokeUtil instance;

    public Object invoke(Invocable invocable){
        try {
            return invocable.invoke(collectInvocableParameters(invocable, RequestRepository.getRequest()));
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public Object[] collectInvocableParameters(Invocable invocable, Request<UndefinedBody> request) {
        short paramsLength = (short) invocable.getMethodParams().length;
        Object[] resultParams = new Object[paramsLength];
        AnnotatedParameter[] parameters = invocable.getMethodParams();

        fillPathVariables(((Mapping) invocable).buildFullPath(), request);
        fillRequestParams(request);

        for (short i = 0; i < paramsLength; i++) {
            if (parameters[i].hasAnnotation(WSRequestBody.class)) {
                try {
                    resultParams[i] = request.getBody().convertToRealBody(parameters[i].getType()); // TODO: 17.08.2023  maybe can try to optimize the "convertToRealBody"
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(String.format(BAD_REQUEST_BODY, request.getSessionId(), request.getPath()));
                    resultParams[i] = null;
                }
            } else if (parameters[i].getClazz() == Request.class && parameters[i].hasGenerics()) {
                try {
                    // There may be a problem if the generic for the body is not the first in the request

                    resultParams[i] = new Request<>(request.getSessionId(), request.getPath(),
                            request.getBody().convertToRealBody(parameters[i].getGenerics()[0].getType()));
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(String.format(BAD_REQUEST_BODY, request.getSessionId(), request.getPath()));

                    resultParams[i] = new Request<>(request.getSessionId(), request.getPath(), null);
                }
            } else if (parameters[i].hasAnnotation(WSPathVariable.class)) {

                // TODO: Refactor this sh*t

                WSPathVariable annotation = (WSPathVariable) parameters[i].getAnnotation(WSPathVariable.class);
                String pathVariableName = StringUtils.isNotBlank(annotation.name()) ? annotation.name() : parameters[i].getName();

                if (request.getPathVariables().containsKey(pathVariableName)) {
                    resultParams[i] = OBJECT_MAPPER.convertValue(request.getPathVariables().get(pathVariableName),
                            parameters[i].getType());
                }

            } else {
                // TODO: 18.08.2023 Here, the data should be taken from beans or some global data storage
//                resultParams[i] = infoService.getSomeInfo(parameters[i].getClazz());
            }
        }
        return resultParams;
    }

    private void fillPathVariables(String fullPath, Request<UndefinedBody> request) {
        String requestPath = request.getPath();
        if (requestPath == null) {
            return;
        }

        String[] fullPathArgs = fullPath.split("\\/");
        String[] requestPathArgs = requestPath.split("\\/");

        for (int i = 0; i < fullPathArgs.length; ++i) {
            String fullPathArg = fullPathArgs[i]; //{id}
            String requestPathArg = requestPathArgs[i];

            if (StringUtils.contains(fullPathArg, '{') &&
                    StringUtils.contains(fullPathArg, '}')) {
                String pathVariableName = fullPathArg.replace("{", "").replace("}", "");
                request.getPathVariables().put(pathVariableName, requestPathArg);
            }
        }
    }

    private void fillRequestParams(Request<UndefinedBody> request) {
        String requestPath = request.getPath();
        if (requestPath == null) {
            return;
        }

        String cutToParams = requestPath.substring(requestPath.indexOf('?') + 1);
        StringBuilder builder = new StringBuilder();
        String lastKey = null;
        for (int i = 0; i < cutToParams.length(); ++i) {
            char ch = cutToParams.charAt(i);
            if (ch == '=') {
                lastKey = builder.toString();
                request.getRequestParams().put(lastKey, null);
                builder.setLength(0);
                continue;
            }
            if (ch == '&' || cutToParams.length() - i == 1) {
                request.getRequestParams().put(lastKey, builder.toString());
                builder.setLength(0);
                continue;
            }
            builder.append(ch);
        }

        /*

        String[] splitByAndOperator = cutToParams.split("&");
        for(String fullRequestParam: splitByAndOperator){
            String[] distinguish = fullRequestParam.split("=");
            request.getRequestParams().put(distinguish[0], distinguish[1]);
        }

         */

    }

    @Override
    public void afterPropertiesSet() {
        instance = this;
    }

    public static InvokeUtil getINSTANCE(){
        return instance;
    }
}
