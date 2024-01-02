package com.jjerome.reflection.context;


import com.jjerome.core.Request;
import com.jjerome.core.UndefinedBody;

import java.lang.annotation.Annotation;
import java.util.function.BiFunction;

public class AnnotatedParameter extends MethodParameter {

    private final Annotation[] annotations;

    private final byte annotationsLength;

    private final ParameterType parameterType;

    private final BiFunction<Request<UndefinedBody>, AnnotatedParameter, Object> extractFunction;

    public AnnotatedParameter(String name, Annotation[] annotations, Class<?> clazz, MethodParameter[] generics,
                              ParameterType parameterType) {
        super(name, clazz, generics);
        this.annotations = annotations;
        this.annotationsLength = (byte) annotations.length;
        this.parameterType = parameterType;
        this.extractFunction = parameterType.getFunction();
    }

    public AnnotatedParameter(String name, Annotation[] annotations, Class<?> clazz, ParameterType parameterType) {
        this(name, annotations, clazz, new MethodParameter[]{}, parameterType);
    }

    public Object extractParameterValue(Request<UndefinedBody> request) {
        return extractFunction.apply(request, this);
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
