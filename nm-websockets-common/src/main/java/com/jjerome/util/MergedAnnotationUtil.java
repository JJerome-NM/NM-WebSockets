package com.jjerome.util;

import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@Component
public class MergedAnnotationUtil {

    public Annotation[] findAllAnnotations(Class<?> element){
        return Stream.of(element.getDeclaredAnnotations())
                .map(annotation -> findMergedAnnotation(element, annotation.annotationType()))
                .toArray(Annotation[]::new);
    }

    public Annotation[] findAllAnnotations(Method element){
        return Stream.of(element.getDeclaredAnnotations())
                .map(annotation -> findMergedAnnotation(element, annotation.annotationType()))
                .toArray(Annotation[]::new);
    }
}
