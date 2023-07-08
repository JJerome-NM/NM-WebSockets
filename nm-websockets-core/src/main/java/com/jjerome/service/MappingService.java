package com.jjerome.service;

import com.fasterxml.jackson.databind.JavaType;
import com.jjerome.annotation.WSPathVariable;
import com.jjerome.annotation.WSRequestBody;
import com.jjerome.domain.Controller;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.Mapping;
import com.jjerome.domain.MethodParameter;
import com.jjerome.domain.Request;
import com.jjerome.domain.UndefinedBody;
import com.jjerome.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class MappingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingService.class);

    private static final String BAD_REQUEST_BODY = "%s wants to request %s but passes the wrong request body";

    private final ControllersStorage controllersStorage;

    private final GlobalInfoService infoService;

    private final ResponseHandler responseHandler;

    public Object invokeMapping(Mapping mapping, Request<UndefinedBody> request){
        Method method = mapping.getMethod();
        Controller controller = controllersStorage.getController(mapping.getControllerClazz());

        try {
            Object[] methodParameters = collectMappingParameters(mapping, request);

            return method.invoke(controller.getSpringBean(), methodParameters);
        } catch (InvocationTargetException | IllegalAccessException e){
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public Object[] collectMappingParameters(Mapping mapping, Request<UndefinedBody> request){
        MethodParameter[] methodParameters = mapping.getMethodParams();
        short methodParametersLength = (short) methodParameters.length;
        Object[] resultParameters = new Object[methodParametersLength];

        for (short i = 0; i < methodParametersLength; i++){
            MethodParameter parameter = methodParameters[i];

            if (parameter.hasAnnotation(WSRequestBody.class)){
                try {
                    resultParameters[i] = request.getBody().convertToRealBody(parameter.converToJavaType());
                } catch (IllegalArgumentException e){
                    resultParameters[i] = null;

                    LOGGER.warn(String.format(BAD_REQUEST_BODY, request.getSessionId(), request.getPath()));
                }
                resultParameters[i] = request.getBody().convertToRealBody(parameter.converToJavaType());
            } else if (parameter.getClazz() == Request.class && parameter.hasGenerics()){
                try {

                    // There may be a problem if the generic for the body is not the first in the request

                    resultParameters[i] = new Request<>(request.getSessionId(), request.getPath(),
                            request.getBody().convertToRealBody(parameter.getGenerics()[0].converToJavaType()));
                } catch (IllegalArgumentException e){
                    resultParameters[i] = new Request<>(request.getSessionId(), request.getPath(), null);

                    LOGGER.warn(String.format(BAD_REQUEST_BODY, request.getSessionId(), request.getPath()));
                }
            } else if (parameter.hasAnnotation(WSPathVariable.class)) {
                resultParameters[i] = null;
            } else {
                resultParameters[i] = infoService.getSomeInfo(parameter.getClazz());
            }
        }
        return resultParameters;
    }
}
