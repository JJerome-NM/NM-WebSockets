package com.jjerome.core;


import com.jjerome.core.enums.WSMappingType;
import com.jjerome.reflection.context.AnnotatedParameter;
import com.jjerome.reflection.context.MethodParameter;
import com.jjerome.reflection.context.annotation.WSMapping;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public interface Mapping extends Invocable, AnnotatedComponent<WSMapping> {

    WSMappingType getType();

    Controller getController();

    Method getMethod();

    String buildFullPath();

    String[] getPathVariablesNames();

    Pattern getRegexPathPattern();

    Request<UndefinedBody> applyRequestFieldsCollectFunctions(Request<UndefinedBody> request);

    void applyInvokeFunction(Request<UndefinedBody> request);

    MappingBuilder<? extends Mapping> toBuilder();

    interface MappingBuilder<T extends Mapping> extends AnnotatedComponentBuilder<WSMapping, T>, Builder<T>{
        MappingBuilder<T> type(WSMappingType type);

        MappingBuilder<T> controller(Controller controller);

        MappingBuilder<T> method(Method method);

        MappingBuilder<T> methodParams(AnnotatedParameter[] parameters);

        MappingBuilder<T> methodReturnType(MethodParameter returnType);

        MappingBuilder<T> pathVariablesNames(String[] pathVariablesNames);

        MappingBuilder<T> regexPathPattern(String regexPath);

        MappingBuilder<T> requestFieldsCollectFunctions(
                BiConsumer<Request<UndefinedBody>, Mapping>[] collectFunctions);

        MappingBuilder<T> invokeFunction(
                MappingInvokeStrategy invokeStrategy);
    }
}
