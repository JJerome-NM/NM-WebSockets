package com.jjerome.domain;

import com.jjerome.context.MethodParameter;
import com.jjerome.context.Parameter;
import com.jjerome.context.annotation.WSMapping;
import com.jjerome.core.Controller;
import com.jjerome.core.Mapping;
import com.jjerome.core.enums.WSMappingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class DefaultMapping implements Mapping {

    private WSMappingType type;

    private WSMapping mappingAnnotation;

    private Controller controller;

    private Method method;

    private MethodParameter[] methodParams;

    private Parameter methodReturnType;

    @Override
    public Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(controller.getSpringBean(), methodParameters);
    }
}
