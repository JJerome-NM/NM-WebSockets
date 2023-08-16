package com.jjerome.util;

import com.jjerome.context.anotation.WSPathVariable;
import com.jjerome.context.anotation.WSRequestBody;
import com.jjerome.core.Mapping;
import com.jjerome.core.Request;
import com.jjerome.core.UndefinedBody;
import com.jjerome.service.GlobalInfoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class MappingUtil { // TODO: 17.08.2023 Reword throws

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingUtil.class);

    private static final String BAD_REQUEST_BODY = "%s wants to request %s but passes the wrong request body";


    private final GlobalInfoService infoService;


    public Object invokeMapping(Mapping mapping, Request<UndefinedBody> request) throws InvocationTargetException, IllegalAccessException{
        return mapping.invoke(collectMappingParameters(mapping, request));
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
