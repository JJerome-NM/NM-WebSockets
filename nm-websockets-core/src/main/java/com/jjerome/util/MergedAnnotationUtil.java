package com.jjerome.util;

import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@Component
public class MergedAnnotationUtil {

    public Annotation[] findAllAnnotations(Class<?> element){
        Annotation[] allElementAnnotations = element.getDeclaredAnnotations();
        int allElementAnnotationsLength = allElementAnnotations.length;

        Annotation[] allMergedAnnotations = new Annotation[allElementAnnotationsLength];

        for (int i = 0; i < allElementAnnotationsLength; i++){
            allMergedAnnotations[i] = findMergedAnnotation(element, allElementAnnotations[i].annotationType());;
        }
        return allMergedAnnotations;
    }
}
