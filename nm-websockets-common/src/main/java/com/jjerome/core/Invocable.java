package com.jjerome.core;

import com.jjerome.reflection.context.MethodParameter;
import com.jjerome.reflection.context.Parameter;

import java.lang.reflect.InvocationTargetException;

public interface Invocable {

    MethodParameter[] getMethodParams();

    Parameter getMethodReturnType();

    Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException;

    default boolean returnsResponse(){
        Parameter parameter = getMethodReturnType();
        return parameter != null && parameter.getClazz() != void.class;
    }
}
