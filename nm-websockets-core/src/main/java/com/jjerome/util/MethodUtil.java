package com.jjerome.util;

import com.jjerome.domain.MethodParameters;
import com.jjerome.domain.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
        int paramsTypesLength = paramsTypes.length;

        Parameter[] parameters = new Parameter[paramsTypesLength];

        for (int i = 0; i < paramsTypesLength; i++){
            parameters[i] = extractGeneric(paramsTypes[i]);
        }

        return parameters;
    }

    public MethodParameters[] extractMethodParameters(Method method){
        Annotation[][] annotations = method.getParameterAnnotations();
        Type[] parametersTypes = method.getGenericParameterTypes();
        int parametersLength = parametersTypes.length;

        MethodParameters[] parameters = new MethodParameters[parametersLength];

        for (int i = 0; i < parametersLength; i++){
            if (parametersTypes[i] instanceof ParameterizedType pType){
                if (pType.getRawType() instanceof Class<?> rawType){
                    parameters[i] = new MethodParameters(annotations[i], rawType,
                            extractGenerics(pType.getActualTypeArguments()));
                }
            } else if (parametersTypes[i] instanceof Class<?> cType) {
                parameters[i] = new MethodParameters(annotations[i], cType);
            }
        }
        return parameters;
    }

    public Parameter extractMethodReturnParameter(Method method){
        return extractGeneric(method.getGenericReturnType());
    }
}
