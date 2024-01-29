package com.jjerome.filters;

import com.jjerome.reflection.context.annotation.DenyAll;
import com.jjerome.exeption.AccessToMappingIsDenyException;

import java.lang.annotation.Annotation;

public class DenyAllFilter extends AbstractSecurityFilter{

    private final Annotation[] annotations;

    private final DenyAll baseAnnotation;

    private int order;


    public DenyAllFilter(DenyAll baseAnnotation, Annotation[] annotations) {
        this.baseAnnotation = baseAnnotation;
        this.annotations = annotations;
        this.order = APPLICATION_SECURITY_PRECEDENCE;
    }

    public DenyAllFilter(Annotation baseAnnotation, Annotation[] annotations) {
        this((DenyAll) baseAnnotation, annotations);
    }

    @Override
    public void doFilter() {
        throw new AccessToMappingIsDenyException();
    }

    @Override
    public String getLabel() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }
}
