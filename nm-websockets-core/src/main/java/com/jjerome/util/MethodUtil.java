package com.jjerome.util;

import com.jjerome.domain.MethodParameter;
import com.jjerome.domain.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class MethodUtil {

    public Parameter extractGeneric(Type paramType){
        if (paramType instanceof ParameterizedType pType){
            if (pType.getRawType() instanceof Class<?> rawType){
                return new Parameter(rawType, extractGenerics(pType.getActualTypeArguments()));
            }
        } else if (paramType instanceof Class<?> cType) {
            return new Parameter(cType);
        }
        return null;
    }

    public Parameter[] extractGenerics(Type[] paramsTypes){
        return Stream.of(paramsTypes).map(this::extractGeneric).toArray(Parameter[]::new);
    }

    public MethodParameter[] extractMethodParameters(Method method){
        Annotation[][] annotations = method.getParameterAnnotations();
        Type[] parametersTypes = method.getGenericParameterTypes();
        int parametersLength = parametersTypes.length;

        MethodParameter[] parameters = new MethodParameter[parametersLength];

        for (int i = 0; i < parametersLength; i++){
            if (parametersTypes[i] instanceof ParameterizedType pType){
                if (pType.getRawType() instanceof Class<?> rawType){
                    parameters[i] = new MethodParameter(annotations[i], rawType,
                            extractGenerics(pType.getActualTypeArguments()));
                }
            } else if (parametersTypes[i] instanceof Class<?> cType) {
                parameters[i] = new MethodParameter(annotations[i], cType);
            }
        }
        return parameters;
    }

    public Parameter extractMethodReturnParameter(Method method){
        return extractGeneric(method.getGenericReturnType());
    }
}
