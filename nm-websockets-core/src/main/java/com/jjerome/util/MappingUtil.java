package com.jjerome.util;

import com.jjerome.context.MethodParameter;
import com.jjerome.context.anotation.WSPathVariable;
import com.jjerome.context.anotation.WSRequestBody;
import com.jjerome.core.Mapping;
import com.jjerome.core.Request;
import com.jjerome.core.UndefinedBody;
import com.jjerome.service.GlobalInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class MappingUtil { // TODO: 17.08.2023 Reword throws

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingUtil.class);

    private static final String BAD_REQUEST_BODY = "%s wants to request %s but passes the wrong request body";

    private final GlobalInfoService infoService;

    public MappingUtil(GlobalInfoService infoService) {
        this.infoService = infoService;
    }


    public Object invokeMapping(Mapping mapping, Request<UndefinedBody> request) throws InvocationTargetException, IllegalAccessException {
        return mapping.invoke(collectMappingParameters(mapping, request));
    }

    public Object[] collectMappingParameters(Mapping mapping, Request<UndefinedBody> request) {
        int paramsLength = mapping.getMethodParams().length;
        Object[] resultParams = new Object[paramsLength];
        MethodParameter[] parameters = mapping.getMethodParams();

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
                resultParams[i] = null;
            } else {
                resultParams[i] = infoService.getSomeInfo(parameters[i].getClazz());
            }
        }
        return resultParams;
    }
}
