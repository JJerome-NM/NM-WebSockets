package com.jjerome.domain;

import com.jjerome.reflection.context.MethodParameter;
import com.jjerome.reflection.context.Parameter;
import com.jjerome.reflection.context.annotation.WSMapping;
import com.jjerome.core.Controller;
import com.jjerome.core.Mapping;
import com.jjerome.core.enums.WSMappingType;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultMapping implements Mapping {

    private final Annotation[] annotations;

    private final WSMappingType type;

    private final WSMapping mappingAnnotation;

    private final Controller controller;

    private final Method method;

    private final MethodParameter[] methodParams;

    private final Parameter methodReturnType;

    public DefaultMapping(Annotation[] annotations, WSMappingType type, WSMapping mappingAnnotation,
                          Controller controller, Method method, MethodParameter[] methodParams,
                          Parameter methodReturnType) {
        this.annotations = annotations;
        this.type = type;
        this.mappingAnnotation = mappingAnnotation;
        this.controller = controller;
        this.method = method;
        this.methodParams = methodParams;
        this.methodReturnType = methodReturnType;
    }

    @Override
    public Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(controller.getSpringBean(), methodParameters);
    }

    @Override
    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public WSMapping getComponentAnnotation() {
        return mappingAnnotation;
    }

    @Override
    public Object getSpringBean() {
        return controller.getSpringBean();
    }

    @Override
    public String buildFullPath() {
        return controller.buildFullPath(this);
    }

    @Override
    public WSMappingType getType() {
        return type;
    }

    public WSMapping getMappingAnnotation() {
        return mappingAnnotation;
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public MethodParameter[] getMethodParams() {
        return methodParams;
    }

    @Override
    public Parameter getMethodReturnType() {
        return methodReturnType;
    }

    public static DefaultMappingBuilder builder(){
        return new DefaultMappingBuilder();
    }

    public static class DefaultMappingBuilder implements MappingBuilder<DefaultMapping>{

        private Annotation[] annotations;

        private WSMappingType type;

        private WSMapping componentAnnotation;

        private Controller controller;

        private Method method;

        private MethodParameter[] methodParams;

        private Parameter methodReturnType;

        @Override
        public DefaultMapping build() {
            return new DefaultMapping(annotations, type, componentAnnotation, controller, method, methodParams, methodReturnType);
        }

        @Override
        public DefaultMappingBuilder annotations(Annotation[] annotations) {
            this.annotations = annotations;
            return this;
        }

        @Override
        public DefaultMappingBuilder componentAnnotation(WSMapping componentAnnotation) {
            this.componentAnnotation = componentAnnotation;
            return this;
        }

        @Override
        public DefaultMappingBuilder springBean(Object springBean) {
            return this;
        }

        @Override
        public DefaultMappingBuilder type(WSMappingType type) {
            this.type = type;
            return this;
        }

        @Override
        public DefaultMappingBuilder controller(Controller controller) {
            this.controller = controller;
            return this;
        }

        @Override
        public DefaultMappingBuilder method(Method method) {
            this.method = method;
            return this;
        }

        @Override
        public DefaultMappingBuilder methodParams(MethodParameter[] parameters) {
            this.methodParams = parameters;
            return this;
        }

        @Override
        public DefaultMappingBuilder methodReturnType(Parameter returnType) {
            this.methodReturnType = returnType;
            return this;
        }
    }
}
