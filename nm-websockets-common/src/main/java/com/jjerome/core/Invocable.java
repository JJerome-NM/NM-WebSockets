package com.jjerome.core;

import com.jjerome.context.MethodParameter;
import com.jjerome.context.Parameter;

import java.lang.reflect.InvocationTargetException;

public interface Invocable {

    MethodParameter[] getMethodParams();

    Parameter getMethodReturnType();

    Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException;

    default boolean returnsResponse(){
        return getMethodReturnType().getClazz() != void.class;
    }
}
