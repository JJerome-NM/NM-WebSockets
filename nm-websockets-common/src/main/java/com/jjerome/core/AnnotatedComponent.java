package com.jjerome.core;

import java.lang.annotation.Annotation;

public interface AnnotatedComponent<A> {

    Annotation[] getAnnotations();

    A getComponentAnnotation();

    Object getSpringBean();

    default <T extends Annotation> T getAnnotation(Class<T> type) {
        for (Annotation ann : getAnnotations()) {
            if (ann.annotationType() == type) {
                return (T) ann;
            }
        }
        return null;
    }

    default <T extends Annotation> boolean containsAnnotation(Class<T> type) {
        return getAnnotation(type) != null;
    }

    interface AnnotatedComponentBuilder<A, T> extends Builder<T> {
        AnnotatedComponentBuilder<A, T> annotations(Annotation[] annotations);

        AnnotatedComponentBuilder<A, T> componentAnnotation(A componentAnnotation);

        AnnotatedComponentBuilder<A, T> springBean(Object springBean);
    }
}
