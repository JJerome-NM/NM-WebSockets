package com.jjerome.domain;

import com.jjerome.core.Controller;
import com.jjerome.core.Mapping;
import com.jjerome.core.enums.WSMappingType;
import com.jjerome.reflection.context.AnnotatedParameter;
import com.jjerome.reflection.context.MethodParameter;
import com.jjerome.reflection.context.annotation.WSMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class ReadOnlyMapping implements Mapping {

    private final Annotation[] annotations;

    private final WSMappingType type;

    private final WSMapping mappingAnnotation;

    private final Controller controller;

    private final Method method;

    private final AnnotatedParameter[] methodParams;

    private final MethodParameter methodReturnType;

    private final String[] pathVariablesNames;

    private final Pattern pathPattern;

    public ReadOnlyMapping(Annotation[] annotations, WSMappingType type, WSMapping mappingAnnotation,
                           Controller controller, Method method, AnnotatedParameter[] methodParams,
                           MethodParameter methodReturnType, String[] pathVariablesNames, Pattern pathPattern) {
        this.annotations = annotations;
        this.type = type;
        this.mappingAnnotation = mappingAnnotation;
        this.controller = controller;
        this.method = method;
        this.methodParams = methodParams;
        this.methodReturnType = methodReturnType;
        this.pathVariablesNames = pathVariablesNames;
        this.pathPattern = pathPattern;
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
    public String[] getPathVariablesNames() {
        return pathVariablesNames;
    }

    @Override
    public Pattern getRegexPathPattern() {
        return pathPattern;
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
    public AnnotatedParameter[] getMethodParams() {
        return methodParams;
    }

    @Override
    public MethodParameter getMethodReturnType() {
        return methodReturnType;
    }

    public static DefaultMappingBuilder builder(){
        return new DefaultMappingBuilder();
    }

    public static class DefaultMappingBuilder implements MappingBuilder<ReadOnlyMapping> {

        private Annotation[] annotations;

        private WSMappingType type;

        private WSMapping componentAnnotation;

        private Controller controller;

        private Method method;

        private AnnotatedParameter[] methodParams;

        private MethodParameter methodReturnType;

        private String[] pathVariablesNames;

        private Pattern regexPathPattern;

        @Override
        public ReadOnlyMapping build() {
            return new ReadOnlyMapping(annotations, type, componentAnnotation, controller, method, methodParams,
                    methodReturnType, pathVariablesNames, regexPathPattern);
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
        public DefaultMappingBuilder methodParams(AnnotatedParameter[] parameters) {
            this.methodParams = parameters;
            return this;
        }

        @Override
        public DefaultMappingBuilder methodReturnType(MethodParameter returnType) {
            this.methodReturnType = returnType;
            return this;
        }

        public MappingBuilder<ReadOnlyMapping> pathVariablesNames(String[] pathVariablesNames) {
            this.pathVariablesNames = pathVariablesNames;
            return this;
        }

        @Override
        public MappingBuilder<ReadOnlyMapping> regexPathPattern(String regexPath) {
            this.regexPathPattern = Pattern.compile(regexPath);
            return this;
        }
    }
}
