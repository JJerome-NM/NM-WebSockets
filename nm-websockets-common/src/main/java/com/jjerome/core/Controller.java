package com.jjerome.core;

import com.jjerome.context.annotation.WSController;

import java.lang.annotation.Annotation;

public interface Controller extends AnnotatedComponent<WSController> {

    Class<?> getClazz();

    String buildFullPath(Mapping mapping);
}
