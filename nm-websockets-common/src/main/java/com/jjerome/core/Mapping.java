package com.jjerome.core;


import com.jjerome.core.enums.WSMappingType;
import com.jjerome.reflection.context.AnnotatedParameter;
import com.jjerome.reflection.context.MethodParameter;
import com.jjerome.reflection.context.annotation.WSMapping;

import java.lang.reflect.Method;

public interface Mapping extends Invocable, AnnotatedComponent<WSMapping> {

    WSMappingType getType();

    Controller getController();

    Method getMethod();

    String buildFullPath();

    String[] getPathVariablesNames();

    interface MappingBuilder<T extends Mapping> extends AnnotatedComponentBuilder<WSMapping, T>, Builder<T>{
        MappingBuilder<T> type(WSMappingType type);

        MappingBuilder<T> controller(Controller controller);

        MappingBuilder<T> method(Method method);

        MappingBuilder<T> methodParams(AnnotatedParameter[] parameters);

        MappingBuilder<T> methodReturnType(MethodParameter returnType);

        MappingBuilder<T> getPathVariablesNames(String[] pathVariablesNames);
    }
}
