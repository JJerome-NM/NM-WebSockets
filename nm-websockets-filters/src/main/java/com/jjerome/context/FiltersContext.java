package com.jjerome.context;

import com.jjerome.Filter;
import com.jjerome.FilterComponent;
import com.jjerome.context.annotation.WSFilter;
import com.jjerome.context.annotation.WSFiltersComponent;
import com.jjerome.core.InitialClass;
import com.jjerome.util.LoggerUtil;
import com.jjerome.util.MergedAnnotationUtil;
import com.jjerome.util.MethodUtil;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import static org.springframework.core.annotation.AnnotatedElementUtils.hasAnnotation;

@Component
public class FiltersContext {

    private final ApplicationContext context;

    private final MethodUtil methodUtil;

    private final MergedAnnotationUtil mergedAnnotationUtil;

    private final InitialClass initialClass;

    public FiltersContext(ApplicationContext context, MethodUtil methodUtil,
                          MergedAnnotationUtil mergedAnnotationUtil, InitialClass initialClass) {
        this.context = context;
        this.methodUtil = methodUtil;
        this.mergedAnnotationUtil = mergedAnnotationUtil;
        this.initialClass = initialClass;
    }

    public List<FilterComponent> findAllFilterComponents(){
        LoggerUtil.disableReflectionsInfoLogs();

        List<FilterComponent> filterComponents = new ArrayList<>();
        Reflections initPackage = new Reflections(initialClass.getClazz().getPackageName());
        Set<Class<?>> allFilterComponents = initPackage.getTypesAnnotatedWith(WSFiltersComponent.class);

        for (String pack : initialClass.getBasePackages()){
            allFilterComponents.addAll(new Reflections(pack).getTypesAnnotatedWith(WSFiltersComponent.class));
        }

        initialClass.getBaseClasses().stream()
                .filter(clazz -> hasAnnotation(clazz, WSFiltersComponent.class))
                .forEach(allFilterComponents::add);

        WSFiltersComponent controllerAnnotation;
        Annotation[] annotations;
        Object controllerBean;

        for(Class<?> filterClazz : allFilterComponents){
            controllerAnnotation = findMergedAnnotation(filterClazz, WSFiltersComponent.class);
            annotations = mergedAnnotationUtil.findAllAnnotations(filterClazz);
            controllerBean = context.getBean(filterClazz);

            filterComponents.add(new FilterComponent(annotations, controllerAnnotation, filterClazz, controllerBean));
        }
        LoggerUtil.enableReflectionsLogs();
        return filterComponents;
    }

    public Map<String, Filter> findAllFilters(List<FilterComponent> filterComponents){
        Map<String, Filter> filterChains = new HashMap<>();

        WSFilter filterAnnotation;
        Filter filter;

        for (FilterComponent filterComponent : filterComponents){
            for(Method method : filterComponent.getClazz().getDeclaredMethods()){
                filterAnnotation = findMergedAnnotation(method, WSFilter.class);

                if (filterAnnotation == null) {
                    continue;
                }

                filter = Filter.builder()
                        .annotations(mergedAnnotationUtil.findAllAnnotations(method))
                        .filterComponent(filterComponent)
                        .componentAnnotation(filterAnnotation)
                        .method(method)
                        .methodParams(methodUtil.extractMethodParameters(method))
                        .methodReturnType(methodUtil.extractMethodReturnParameter(method))
                        .build();

                filterChains.put(filter.getLabel(), filter);
            }
        }
        return filterChains;
    }
}
