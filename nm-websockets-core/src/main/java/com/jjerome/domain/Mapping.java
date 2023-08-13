package com.jjerome.domain;

import com.jjerome.context.MethodParameter;
import com.jjerome.context.Parameter;
import com.jjerome.context.anotation.WSMapping;
import com.jjerome.enums.WSMappingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Method;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Mapping {

    private WSMappingType type;

    private WSMapping mappingAnnotation;

    private Class<?> controllerClazz;

    private Method method;

    private MethodParameter[] methodParams;

    private Parameter methodReturnType;
}
