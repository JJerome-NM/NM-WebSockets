package com.jjerome;

import com.jjerome.context.MethodParameter;
import com.jjerome.context.Parameter;
import com.jjerome.context.annotation.WSMapping;
import com.jjerome.core.Controller;
import com.jjerome.core.Mapping;
import com.jjerome.core.enums.WSMappingType;

import java.lang.annotation.Annotation;
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
        filterChain.doFilter();

        return mapping.invoke(methodParameters);
    }

    @Override
    public String buildFullPath() {
        return mapping.buildFullPath();
    }

    @Override
    public WSMappingType getType() {
        return mapping.getType();
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

    @Override
    public Annotation[] getAnnotations() {
        return mapping.getAnnotations();
    }

    @Override
    public WSMapping getComponentAnnotation() {
        return mapping.getComponentAnnotation();
    }

    @Override
    public Object getSpringBean() {
        return mapping.getSpringBean();
    }
}
