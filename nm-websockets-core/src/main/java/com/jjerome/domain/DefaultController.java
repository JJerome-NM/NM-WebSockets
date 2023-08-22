package com.jjerome.domain;

import com.jjerome.context.annotation.WSController;
import com.jjerome.core.Controller;
import com.jjerome.core.Mapping;

import java.lang.annotation.Annotation;

public class DefaultController implements Controller {

    private final Annotation[] annotations;

    private final WSController controllerAnnotation;

    private final Class<?> clazz;

    private final Object springBean;

    public DefaultController(Annotation[] annotations, WSController controllerAnnotation,
                             Class<?> clazz, Object springBean) {
        this.annotations = annotations;
        this.controllerAnnotation = controllerAnnotation;
        this.clazz = clazz;
        this.springBean = springBean;
    }

    @Override
    public String buildFullPath(Mapping mapping) {
        return controllerAnnotation.pathPrefix() + mapping.getComponentAnnotation().path();
    }

    @Override
    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public WSController getComponentAnnotation() {
        return controllerAnnotation;
    }

    @Override
    public Object getSpringBean() {
        return springBean;
    }
}
