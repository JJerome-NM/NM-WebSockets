package com.jjerome.reflection.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjerome.core.Request;
import com.jjerome.core.RequestRepository;
import com.jjerome.core.UndefinedBody;
import com.jjerome.reflection.context.annotation.WSPathVariable;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.function.BiFunction;

public enum ParameterType {

    UNDEFINED_DATA((request, parameter) -> {
        Request<?> result;

        if ((result = RequestRepository.getRequestWithRealBody()) != null) {
            return result;
        }

        try {
            result = request.getBody().convertToRealBody(parameter.getType());
            RequestRepository.setRequestWithRealBody(result);
            return result;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }),
    REQUEST(((request, parameter) -> {
        Request<?> result;

        if ((result = RequestRepository.getRequestWithRealBody()) != null) {
            return result;
        }

        try {
            // There may be a problem if the generic for the body is not the first in the request
            result = new Request<>(request.getSessionId(), request.getPath(),
                    request.getBody().convertToRealBody(parameter.getGenerics()[0].getType()));
        } catch (IllegalArgumentException e) {
            result = new Request<>(request.getSessionId(), request.getPath(), null);
        }

        result.setRequestParams(new HashMap<>(request.getRequestParams()));
        result.setPathVariables(new HashMap<>(request.getPathVariables()));
        RequestRepository.setRequestWithRealBody(result);
        return result;
    })),
    REQUEST_BODY((request, parameter) -> {
        try {
            return request.getBody().convertToRealBody(parameter.getType());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }),
    PATH_VARIABLE((request, parameter) -> {
        WSPathVariable annotation = (WSPathVariable) parameter.getAnnotation(WSPathVariable.class);
        String pathVariableName = StringUtils.isNotBlank(annotation.name()) ? annotation.name() : parameter.getName();

        if (request.getPathVariables().containsKey(pathVariableName)) {
            return getObjectMapper().convertValue(request.getPathVariables().get(pathVariableName),
                    parameter.getType());
        }
        return null;
    }),
    QUERY_PARAM((request, parameter) -> {
        return null;
    }),
    ALL_QUERY_PARAM((request, parameter) -> {
        return null;
    });

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final BiFunction<Request<UndefinedBody>, AnnotatedParameter, Object> function;

    ParameterType(BiFunction<Request<UndefinedBody>, AnnotatedParameter, Object> function) {
        this.function = function;
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public BiFunction<Request<UndefinedBody>, AnnotatedParameter, Object> getFunction() {
        return function;
    }
}
