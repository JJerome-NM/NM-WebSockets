package com.jjerome.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Annotation;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class MethodParameter extends Parameter {

    private Annotation[] annotations;

    public MethodParameter(Annotation[] annotations, Class<?> parametherClass){
        this(annotations, parametherClass, null);
    }

    public MethodParameter(Annotation[] annotations, Class<?> parametherClass, Parameter[] genericClasses){
        super(parametherClass, genericClasses);
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
