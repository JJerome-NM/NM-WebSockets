package com.jjerome.core;

import java.lang.annotation.Annotation;

public interface AnnotatedComponent<A> {

    Annotation[] getAnnotations();

    A getComponentAnnotation();

    Object getSpringBean();
}
