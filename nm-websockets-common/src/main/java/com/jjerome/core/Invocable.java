package com.jjerome.core;

import com.jjerome.reflection.context.AnnotatedParameter;
import com.jjerome.reflection.context.MethodParameter;

import java.lang.reflect.InvocationTargetException;

public interface Invocable {

    AnnotatedParameter[] getMethodParams();

    MethodParameter getMethodReturnType();

    Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException;

    default boolean returnsResponse(){
        MethodParameter parameter = getMethodReturnType();
        return parameter != null && parameter.getClazz() != void.class;
    }
}
