package com.jjerome.util;

import com.jjerome.reflection.context.AnnotatedParameter;
import com.jjerome.reflection.context.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

@Component
public class MethodUtil {

    /*public MethodParameter[] extractMethodParameters(Method method){
        Annotation[][] annotations = method.getParameterAnnotations();
        Type[] parametersTypes = method.getGenericParameterTypes();
        int parametersLength = parametersTypes.length;

        MethodParameter[] parameters = new MethodParameter[parametersLength];

        for (int i = 0; i < parametersLength; i++){
            if (parametersTypes[i] instanceof ParameterizedType pType){
                if (pType.getRawType() instanceof Class<?> rawType){
                    parameters[i] = new MethodParameter(rawType.getName(), annotations[i], rawType,
                            extractGenerics(pType.getActualTypeArguments()));
                }
            } else if (parametersTypes[i] instanceof Class<?> cType) {
                parameters[i] = new MethodParameter(cType.getName(), annotations[i], cType);
            }
        }
        return parameters;
    }*/

    public AnnotatedParameter[] extractMethodParameters(Method method) {
        Annotation[][] annotations = method.getParameterAnnotations();
        java.lang.reflect.Parameter[] methodParameters = method.getParameters();
        Type[] parametersTypes = method.getGenericParameterTypes();
        int parametersLength = parametersTypes.length;

        AnnotatedParameter[] parameters = new AnnotatedParameter[parametersLength];

        for (int i = 0; i < parametersLength; i++) {
            if (parametersTypes[i] instanceof ParameterizedType pType) {
                if (pType.getRawType() instanceof Class<?> rawType) {
                    parameters[i] = new AnnotatedParameter(methodParameters[i].getName(), annotations[i], rawType,
                            extractGenerics(pType.getActualTypeArguments()));
                }
            } else if (parametersTypes[i] instanceof Class<?> cType) {
                parameters[i] = new AnnotatedParameter(methodParameters[i].getName(), annotations[i], cType);
            }
        }
        return parameters;
    }

    public MethodParameter[] extractGenerics(Type[] paramsTypes) {
        return Stream.of(paramsTypes).map(this::extractGeneric).toArray(MethodParameter[]::new);
    }

    public MethodParameter extractGeneric(Type paramType) {
        if (paramType instanceof ParameterizedType pType){
            if (pType.getRawType() instanceof Class<?> rawType){
                return new MethodParameter(rawType.getName(), rawType, extractGenerics(pType.getActualTypeArguments()));
            }
        } else if (paramType instanceof Class<?> cType) {
            return new MethodParameter(cType.getName(), cType);
        }
        return null;
    }

    public MethodParameter extractMethodReturnParameter(Method method) {
        return extractGeneric(method.getGenericReturnType());
    }
}
