package com.jjerome.core;

import java.lang.annotation.Annotation;

public interface AnnotatedComponent<A> {

    Annotation[] getAnnotations();

    A getComponentAnnotation();

    Object getSpringBean();

    interface AnnotatedComponentBuilder<A, T> extends Builder<T>{
        AnnotatedComponentBuilder<A, T> annotations(Annotation[] annotations);

        AnnotatedComponentBuilder<A, T> componentAnnotation(A componentAnnotation);

        AnnotatedComponentBuilder<A, T> springBean(Object springBean);
    }
}
