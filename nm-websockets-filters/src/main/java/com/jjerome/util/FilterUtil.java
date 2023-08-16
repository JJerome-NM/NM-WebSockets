package com.jjerome.util;

import com.jjerome.Filter;
import com.jjerome.core.Request;
import com.jjerome.core.RequestRepository;
import com.jjerome.core.UndefinedBody;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class FilterUtil implements InitializingBean {

    private static FilterUtil instance;


    public void invokeFilterMethod(Filter filterChain) { // TODO: 17.08.2023 All throws rework for ExeprionInterceptor 
        Request<UndefinedBody> request = RequestRepository.getRequest();

        try {
            filterChain.invoke(collectFilterParamethers(filterChain, request));
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object[] collectFilterParamethers(Filter filterChain, Request<UndefinedBody> request){
        return new Object[]{};
    }

    @Override
    public void afterPropertiesSet() {
        instance = this;
    }

    public static FilterUtil getInstance(){
        return instance;
    }
}
