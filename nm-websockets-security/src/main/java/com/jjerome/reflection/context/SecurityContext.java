package com.jjerome.reflection.context;

import com.jjerome.context.annotation.WSController;
import com.jjerome.context.annotation.WSMapping;
import com.jjerome.core.InitialClass;
import com.jjerome.core.filters.Filter;
import com.jjerome.filters.SecurityFiltersFactory;
import com.jjerome.util.MergedAnnotationUtil;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@Component
public class SecurityContext {

    private final InitialClass initialClass;

    private final MergedAnnotationUtil mergedAnnotationUtil;

    private final SecurityFiltersFactory securityFiltersFactory;

    SecurityContext(InitialClass initialClass, MergedAnnotationUtil mergedAnnotationUtil,
                    SecurityFiltersFactory securityFiltersFactory) {
        this.initialClass = initialClass;
        this.mergedAnnotationUtil = mergedAnnotationUtil;
        this.securityFiltersFactory = securityFiltersFactory;
    }

    public Map<String, Set<Filter>> findAllAnnotationFilters() {
        Map<String, Set<Filter>> filters = new HashMap<>();

        Set<Class<?>> classes = initialClass.getBaseClasses().stream()
                .filter(c -> c.isAnnotationPresent(WSController.class)).collect(Collectors.toSet());

        classes.addAll(new Reflections(initialClass.getBasePackages()).getTypesAnnotatedWith(WSController.class));

        for (Class<?> clazz : classes) {
            WSController controller = findMergedAnnotation(clazz, WSController.class);
            for (Method method : clazz.getDeclaredMethods()) {
                WSMapping mapping = findMergedAnnotation(method, WSMapping.class);

                if (mapping == null) {
                    continue;
                }

                Annotation[] annotations = mergedAnnotationUtil.findAllAnnotations(method);
                String mappingPath = controller.pathPrefix() + mapping.path();

                filters.put(mappingPath, securityFiltersFactory.collectFilters(annotations));
            }
        }

        return filters;
    }
}
