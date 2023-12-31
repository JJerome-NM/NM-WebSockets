package com.jjerome.core;


import com.jjerome.reflection.context.MethodParameter;
import com.jjerome.reflection.context.Parameter;
import com.jjerome.reflection.context.annotation.WSMapping;
import com.jjerome.core.enums.WSMappingType;

import java.lang.reflect.Method;

public interface Mapping extends Invocable, AnnotatedComponent<WSMapping> {

    WSMappingType getType();

    Controller getController();

    Method getMethod();

    String buildFullPath();

    interface MappingBuilder<T extends Mapping> extends AnnotatedComponentBuilder<WSMapping, T>, Builder<T>{
        MappingBuilder<T> type(WSMappingType type);

        MappingBuilder<T> controller(Controller controller);

        MappingBuilder<T> method(Method method);

        MappingBuilder<T> methodParams(MethodParameter[] parameters);

        MappingBuilder<T> methodReturnType(Parameter returnType);
    }
}
