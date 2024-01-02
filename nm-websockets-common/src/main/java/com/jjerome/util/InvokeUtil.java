package com.jjerome.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjerome.core.Invocable;
import com.jjerome.core.Request;
import com.jjerome.core.RequestRepository;
import com.jjerome.core.UndefinedBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

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
            throw new RuntimeException(e);
        }
    }

    public Object[] collectInvocableParameters(Invocable invocable, Request<UndefinedBody> request) {
        return Stream.of(invocable.getMethodParams())
                .map(parameter -> parameter.extractParameterValue(request))
                .toArray(Object[]::new);
    }

    @Override
    public void afterPropertiesSet() {
        instance = this;
    }

    public static InvokeUtil getINSTANCE(){
        return instance;
    }
}
