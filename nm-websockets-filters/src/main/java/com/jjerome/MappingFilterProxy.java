package com.jjerome;

import com.jjerome.context.MethodParameter;
import com.jjerome.context.Parameter;
import com.jjerome.context.annotation.WSMapping;
import com.jjerome.core.Controller;
import com.jjerome.core.Mapping;
import com.jjerome.core.enums.WSMappingType;
import com.jjerome.filter.FilterChain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MappingFilterProxy implements Mapping {

    private final FilterChain filterChain;

    private final Mapping mapping;

    MappingFilterProxy(FilterChain filterChain, Mapping realMapping){
        this.filterChain = filterChain;
        this.mapping = realMapping;
    }

    @Override
    public Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException {
        System.out.println("MappingFilterProxy.invoke()");

        filterChain.doFilter();

        return mapping.invoke(methodParameters);
    }


    @Override
    public WSMappingType getType() {
        return mapping.getType();
    }

    @Override
    public WSMapping getMappingAnnotation() {
        return mapping.getMappingAnnotation();
    }

    @Override
    public Controller getController() {
        return mapping.getController();
    }

    @Override
    public Method getMethod() {
        return mapping.getMethod();
    }

    @Override
    public MethodParameter[] getMethodParams() {
        return mapping.getMethodParams();
    }

    @Override
    public Parameter getMethodReturnType() {
        return mapping.getMethodReturnType();
    }
}
