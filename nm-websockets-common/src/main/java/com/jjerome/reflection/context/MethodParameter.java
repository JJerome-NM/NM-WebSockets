package com.jjerome.reflection.context;


import java.lang.annotation.Annotation;

public class MethodParameter extends Parameter {

    private Annotation[] annotations;

    private byte annotationsLength;

    public MethodParameter(Annotation[] annotations, Class<?> clazz, Parameter[] generics) {
        super(clazz, generics);
        this.annotations = annotations;
        this.annotationsLength = (byte) annotations.length;
    }

    public MethodParameter(Annotation[] annotations, Class<?> clazz) {
        super(clazz);
        this.annotations = annotations;
    }

    public Annotation getAnnotation(Class<? extends Annotation> annotationClazz){
        for(byte i = 0; i < annotationsLength; ++i){
            if (annotations[i].annotationType() == annotationClazz){
                return annotations[i];
            }
        }
        return null;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClazz){
        return getAnnotation(annotationClazz) != null;
    }
}
