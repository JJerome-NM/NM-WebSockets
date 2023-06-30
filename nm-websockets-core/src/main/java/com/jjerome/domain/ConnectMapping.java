package com.jjerome.domain;


import com.jjerome.annotation.WSMapping;
import com.jjerome.enums.WSMappingType;

import java.lang.reflect.Method;

public class ConnectMapping extends Mapping{

    ConnectMapping(WSMapping mappingAnnotation, Method method,
                   MethodParameters[] methodParams, Parameter methodReturnType){
        super(WSMappingType.CONNECT, mappingAnnotation, method, methodParams, methodReturnType);
    }
}
