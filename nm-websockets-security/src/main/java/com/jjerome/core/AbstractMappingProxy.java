package com.jjerome.core;


import com.jjerome.core.enums.WSMappingType;
import com.jjerome.reflection.context.AnnotatedParameter;
import com.jjerome.reflection.context.MethodParameter;
import com.jjerome.reflection.context.annotation.WSMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractMappingProxy implements Mapping {

    private final Mapping mapping;


    protected AbstractMappingProxy(Mapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException {
        return mapping.invoke(methodParameters);
    }

    @Override
    public String buildFullPath() {
        return mapping.buildFullPath();
    }

    @Override
    public String[] getPathVariablesNames() {
        return mapping.getPathVariablesNames();
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
    public AnnotatedParameter[] getMethodParams() {
        return mapping.getMethodParams();
    }

    @Override
    public MethodParameter getMethodReturnType() {
        return mapping.getMethodReturnType();
    }

    @Override
    public Annotation[] getAnnotations() {
        return mapping.getAnnotations();
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        return mapping.getAnnotation(type);
    }

    @Override
    public <T extends Annotation> boolean containsAnnotation(Class<T> type) {
        return mapping.containsAnnotation(type);
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
