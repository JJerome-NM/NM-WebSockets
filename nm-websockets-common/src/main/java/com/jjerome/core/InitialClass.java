package com.jjerome.core;


import java.lang.annotation.Annotation;
import java.util.Set;

public class InitialClass implements AnnotatedComponent<Annotation> {

    private final Annotation[] annotations;

    private final Class<?> clazz;

    private final Object springBean;

    private Set<String> basePackages;

    private Set<Class<?>> baseClasses;

    private boolean enableSpringComponentScan;

    public InitialClass(Annotation[] annotations, Class<?> clazz, Object springBean) {
        this.annotations = annotations;
        this.clazz = clazz;
        this.springBean = springBean;
    }

    public void initWSComponentScanFields(Set<String> basePackages, Set<Class<?>> baseClasses, boolean enableSpringComponentScan){
        this.basePackages = basePackages;
        this.baseClasses = baseClasses;
        this.enableSpringComponentScan = enableSpringComponentScan;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public Annotation getComponentAnnotation() {
        return null;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getSpringBean() {
        return springBean;
    }

    public Set<String> getBasePackages() {
        return basePackages;
    }

    public Set<Class<?>> getBaseClasses() {
        return baseClasses;
    }

    public boolean isEnableSpringComponentScan() {
        return enableSpringComponentScan;
    }
}
