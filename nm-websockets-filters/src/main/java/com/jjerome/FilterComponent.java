package com.jjerome;

import com.jjerome.context.annotation.WSFiltersComponent;
import com.jjerome.core.AnnotatedComponent;

import java.lang.annotation.Annotation;


public class FilterComponent implements AnnotatedComponent<WSFiltersComponent> {

    private final Annotation[] annotations;

    private final WSFiltersComponent componentAnnotation;

    private final Class<?> clazz;

    private final Object springBean;

    public FilterComponent(Annotation[] annotations, WSFiltersComponent componentAnnotation,
                           Class<?> clazz, Object springBean){
        this.annotations = annotations;
        this.componentAnnotation = componentAnnotation;
        this.clazz = clazz;
        this.springBean = springBean;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public WSFiltersComponent getComponentAnnotation() {
        return componentAnnotation;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getSpringBean() {
        return springBean;
    }
}
