package com.jjerome.context;

import com.jjerome.context.annotation.WSController;
import com.jjerome.core.InitialClass;
import com.jjerome.core.filters.Filter;
import com.jjerome.util.MergedAnnotationUtil;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SecurityContext {

    private final InitialClass initialClass;

    private final MergedAnnotationUtil mergedAnnotationUtil;

    SecurityContext(InitialClass initialClass, MergedAnnotationUtil mergedAnnotationUtil) {
        this.initialClass = initialClass;
        this.mergedAnnotationUtil = mergedAnnotationUtil;
    }

    public Map<String, Set<Filter>> findAllAnnotationFilters(){
        Map<String, Set<Filter>> filters = new HashMap<>();

        Set<Class<?>> classes = initialClass.getBaseClasses().stream()
                .filter(c -> c.isAnnotationPresent(WSController.class)).collect(Collectors.toSet());

        classes.addAll(new Reflections(initialClass.getBasePackages()).getTypesAnnotatedWith(WSController.class));

        for (Class<?> clazz : classes){
            for (Method method : clazz.getDeclaredMethods()){
                Annotation[] annotations = mergedAnnotationUtil.findAllAnnotations(method);

                System.out.println(annotations);
            }
        }

        return filters;
    }
}
