package com.jjerome.service;

import com.jjerome.annotation.WSPathVariable;
import com.jjerome.annotation.WSRequestBody;
import com.jjerome.domain.Controller;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.Mapping;
import com.jjerome.domain.Request;
import com.jjerome.domain.UndefinedBody;
import com.jjerome.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class MappingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingService.class);

    private static final String BAD_REQUEST_BODY = "%s wants to request %s but passes the wrong request body";

    private final ControllersStorage controllersStorage;

    private final GlobalInfoService infoService;

    private final ResponseHandler responseHandler;

    public Object invokeMapping(Mapping mapping, Request<UndefinedBody> request) {
        Method method = mapping.getMethod();
        Controller controller = controllersStorage.getController(mapping.getControllerClazz());

        try {
            Object[] methodParameters = collectMappingParameters(mapping, request);

            return method.invoke(controller.getSpringBean(), methodParameters);
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public Object[] collectMappingParameters(Mapping mapping, Request<UndefinedBody> request) {
        return Stream.of(mapping.getMethodParams())
                .map(parameter -> {
                    if (parameter.hasAnnotation(WSRequestBody.class)) {
                        try {
                            return request.getBody().convertToRealBody(parameter.converToJavaType());
                        } catch (IllegalArgumentException e) {
                            LOGGER.warn(String.format(BAD_REQUEST_BODY, request.getSessionId(), request.getPath()));
                            return null;
                        }
                    } else if (parameter.getClazz() == Request.class && parameter.hasGenerics()) {
                        try {
                            // There may be a problem if the generic for the body is not the first in the request

                            return new Request<>(request.getSessionId(), request.getPath(),
                                    request.getBody().convertToRealBody(parameter.getGenerics()[0].converToJavaType()));
                        } catch (IllegalArgumentException e) {
                            LOGGER.warn(String.format(BAD_REQUEST_BODY, request.getSessionId(), request.getPath()));

                            return new Request<>(request.getSessionId(), request.getPath(), null);
                        }
                    } else if (parameter.hasAnnotation(WSPathVariable.class)) {
                        return null;
                    } else {
                        return infoService.getSomeInfo(parameter.getClazz());
                    }
                })
                .toArray(Object[]::new);
    }
}
