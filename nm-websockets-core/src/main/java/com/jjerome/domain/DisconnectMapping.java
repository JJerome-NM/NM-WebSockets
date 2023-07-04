package com.jjerome.domain;

import com.jjerome.annotation.WSMapping;
import com.jjerome.enums.WSMappingType;

import java.lang.reflect.Method;

public class DisconnectMapping extends Mapping{

    DisconnectMapping(WSMapping mappingAnnotation, Class<?> controllerClazz, Method method,
                      MethodParameter[] methodParams, Parameter methodReturnType){
        super(WSMappingType.DISCONNECT, mappingAnnotation, controllerClazz, method, methodParams, methodReturnType);
    }
}
