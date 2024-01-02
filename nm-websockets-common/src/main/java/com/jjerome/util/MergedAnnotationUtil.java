package com.jjerome.util;

import org.apache.commons.lang3.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.core.annotation.AnnotatedElementUtils.findAllMergedAnnotations;

@Component
public class MergedAnnotationUtil {

    private static final Map<Class<?>, Class<?>> ARRAY_TYPES_CLASS_MAP = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(String[].class, String.class),
            new AbstractMap.SimpleEntry<>(Class[].class, Class.class),
            new AbstractMap.SimpleEntry<>(int[].class, int.class),
            new AbstractMap.SimpleEntry<>(short[].class, short.class),
            new AbstractMap.SimpleEntry<>(byte[].class, byte.class),
            new AbstractMap.SimpleEntry<>(double[].class, double.class),
            new AbstractMap.SimpleEntry<>(float[].class, float.class),
            new AbstractMap.SimpleEntry<>(boolean[].class, boolean.class),
            new AbstractMap.SimpleEntry<>(long[].class, long.class)
    );


    public Annotation[] findAllAnnotations(AnnotatedElement element) {
        return Stream.of(element.getDeclaredAnnotations())
                .map(annotation -> findAllMergedAnnotationsAndCompareArrays(element, annotation.annotationType()))
                .toArray(Annotation[]::new);
    }

    public <T extends Annotation> T findAllMergedAnnotationsAndCompareArrays(AnnotatedElement element,
                                                                             Class<T> annotationType) {
        Set<T> allAnnotations = findAllMergedAnnotations(element, annotationType);
        if (allAnnotations.size() == 1){
            return allAnnotations.stream().findFirst().orElseThrow();
        }

        Map<String, Object> map = new HashMap<>();

        Stream.of(annotationType.getDeclaredMethods())
                .forEach(method -> map.put(method.getName(), getAnnotationMethodInvokeResult(allAnnotations, method)));

        return mockAnnotation(annotationType, map);
    }

    private Object getAnnotationMethodInvokeResult(Set<?> allAnnotations, Method method){
        if (method.getReturnType().isArray()){
            return compareArraysFromAnnotationsField(allAnnotations, method, ARRAY_TYPES_CLASS_MAP.get(method.getReturnType()));
        }
        try {
            return method.invoke(allAnnotations.stream().findFirst().get());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T[] compareArraysFromAnnotationsField(Set<?> allAnnotations, Method method, Class<T> type){
        List<T> array = new ArrayList<>();
        allAnnotations.forEach(ann -> {
            try {
                array.addAll(Arrays.asList((T[]) method.invoke(ann)));
            } catch (Exception ignored) {
            }
        });
        return array.toArray((T[]) Array.newInstance(type, array.size()));
    }

    public <A extends Annotation> A mockAnnotation(Class<A> annotationClass, Map<String, Object> properties) {
        return (A) Proxy.newProxyInstance(annotationClass.getClassLoader(), new Class<?>[]{annotationClass}, (proxy, method, args) -> {
            Annotation annotation = (Annotation) proxy;
            String methodName = method.getName();

            switch (methodName) {
                case "toString":
                    return AnnotationUtils.toString(annotation);
                case "hashCode":
                    return AnnotationUtils.hashCode(annotation);
                case "equals":
                    return AnnotationUtils.equals(annotation, (Annotation) args[0]);
                case "annotationType":
                    return annotationClass;
                default:
                    if (!properties.containsKey(methodName)) {
                        throw new NoSuchMethodException(String.format("Missing value for mocked annotation property '%s'. Pass the correct value in the 'properties' parameter", methodName));
                    }
                    return properties.get(methodName);
            }
        });
    }
}
