package com.jjerome.reflection.context;


import java.lang.annotation.Annotation;

public class AnnotatedParameter extends MethodParameter {

    private Annotation[] annotations;

    private byte annotationsLength;

    public AnnotatedParameter(String name, Annotation[] annotations, Class<?> clazz, MethodParameter[] generics) {
        super(name, clazz, generics);
        this.annotations = annotations;
        this.annotationsLength = (byte) annotations.length;
    }

    public AnnotatedParameter(String name, Annotation[] annotations, Class<?> clazz) {
        this(name, annotations, clazz, new MethodParameter[]{});
    }

    public Annotation getAnnotation(Class<? extends Annotation> annotationClazz) {
        for (byte i = 0; i < annotationsLength; ++i) {
            if (annotations[i].annotationType() == annotationClazz) {
                return annotations[i];
            }
        }
        return null;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClazz) {
        return getAnnotation(annotationClazz) != null;
    }
}
