package com.jjerome.filters;

import com.jjerome.reflection.context.annotation.HasRole;
import com.jjerome.core.filters.Filter;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Component
public class SecurityFiltersFactory {

    private final Map<Class<? extends Annotation>, BiFunction<Annotation, Annotation[], Filter>> constructorsFiltersMap;

    SecurityFiltersFactory(){
        constructorsFiltersMap = new HashMap<>();
        constructorsFiltersMap.put(HasRole.class, HasAnyRoleFilter::new);
    }

    public Set<Filter> collectFilters(Annotation... annotations){
        return Stream.of(annotations)
                .filter(a -> constructorsFiltersMap.containsKey(a.annotationType()))
                .map(a -> build(a, annotations))
                .collect(toSet());
    }

    public Filter build(Annotation annotation, Annotation... annotations){
        if (!constructorsFiltersMap.containsKey(annotation.annotationType())){
            return null;
        }
        return constructorsFiltersMap.get(annotation.annotationType()).apply(annotation, annotations);
    }
}
