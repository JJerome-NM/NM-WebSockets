package com.jjerome.core;


import com.jjerome.context.MethodParameter;
import com.jjerome.context.Parameter;
import com.jjerome.context.annotation.WSMapping;
import com.jjerome.core.enums.WSMappingType;

import java.lang.reflect.Method;

public interface Mapping extends Invocable, AnnotatedComponent<WSMapping> {

    WSMappingType getType();

    Controller getController();

    Method getMethod();

    MethodParameter[] getMethodParams();

    Parameter getMethodReturnType();

    String buildFullPath();

    interface MappingBuilder<T extends Mapping> extends AnnotatedComponentBuilder<WSMapping, T>, Builder<T>{
        MappingBuilder<T> type(WSMappingType type);

        MappingBuilder<T> controller(Controller controller);

        MappingBuilder<T> method(Method method);

        MappingBuilder<T> methodParams(MethodParameter[] parameters);

        MappingBuilder<T> methodReturnType(Parameter returnType);
    }
}
