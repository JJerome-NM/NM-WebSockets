package com.jjerome.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Annotation;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class MethodParameters extends Parameter {

    private Annotation[] annotations;

    public MethodParameters(Annotation[] annotations, Class<?> parametherClass){
        this(annotations, parametherClass, null);
    }

    public MethodParameters(Annotation[] annotations, Class<?> parametherClass, Parameter[] genericClasses){
        super(parametherClass, genericClasses);
        this.annotations = annotations;
    }

    public Annotation getAnnotation(Class<? extends Annotation> annotationClazz){
        for (Annotation annotation : annotations){
            if (annotation.getClass() == annotationClazz){
                return annotation;
            }
        }
        return null;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClazz){
        return getAnnotation(annotationClazz) != null;
    }
}
