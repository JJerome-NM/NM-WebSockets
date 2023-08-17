package com.jjerome.context;


import java.lang.annotation.Annotation;

public class MethodParameter extends Parameter {

    private Annotation[] annotations;

    public MethodParameter(Annotation[] annotations, Class<?> clazz, Parameter[] generics) {
        super(clazz, generics);
        this.annotations = annotations;
    }

    public MethodParameter(Annotation[] annotations, Class<?> clazz) {
        super(clazz);
        this.annotations = annotations;
    }

    public Annotation getAnnotation(Class<? extends Annotation> annotationClazz){
        for (Annotation annotation : annotations){
            if (annotation.annotationType() == annotationClazz){
                return annotation;
            }
        }
        return null;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClazz){
        return getAnnotation(annotationClazz) != null;
    }
}
