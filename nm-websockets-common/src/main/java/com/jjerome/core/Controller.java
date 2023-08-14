package com.jjerome.core;

import com.jjerome.context.annotation.WSController;

import java.lang.annotation.Annotation;

public interface Controller {

    Annotation[] getAnnotations();

    WSController getControllerAnnotation();

    Class<?> getClazz();

    Object getSpringBean();
}
