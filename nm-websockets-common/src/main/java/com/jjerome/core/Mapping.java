package com.jjerome.core;


import com.jjerome.context.MethodParameter;
import com.jjerome.context.Parameter;
import com.jjerome.context.annotation.WSMapping;
import com.jjerome.core.enums.WSMappingType;

import java.lang.reflect.Method;

public interface Mapping extends Invocable {

    WSMappingType getType();

    WSMapping getMappingAnnotation();

    Controller getController();

    Method getMethod();

    MethodParameter[] getMethodParams();

    Parameter getMethodReturnType();
}
