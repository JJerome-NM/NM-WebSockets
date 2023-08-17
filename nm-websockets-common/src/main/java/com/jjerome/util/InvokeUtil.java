package com.jjerome.util;

import com.jjerome.context.MethodParameter;
import com.jjerome.context.annotation.WSPathVariable;
import com.jjerome.context.annotation.WSRequestBody;
import com.jjerome.core.Invocable;
import com.jjerome.core.Request;
import com.jjerome.core.RequestRepository;
import com.jjerome.core.UndefinedBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class InvokeUtil implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvokeUtil.class);

    private static final String BAD_REQUEST_BODY = "%s wants to request %s but passes the wrong request body";
    
    private static InvokeUtil INSTANCE;

    public Object invoke(Invocable invocable){
        try {
            return invocable.invoke(collectInvocableParameters(invocable, RequestRepository.getRequest()));
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object[] collectInvocableParameters(Invocable invocable, Request<UndefinedBody> request) {
        short paramsLength = (short) invocable.getMethodParams().length;
        Object[] resultParams = new Object[paramsLength];
        MethodParameter[] parameters = invocable.getMethodParams();

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
                // TODO: 18.08.2023 Here, the data should be taken from beans or some global data storage
//                resultParams[i] = infoService.getSomeInfo(parameters[i].getClazz());
            }
        }
        return resultParams;
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    public static InvokeUtil getINSTANCE(){
        return INSTANCE;
    }
}
