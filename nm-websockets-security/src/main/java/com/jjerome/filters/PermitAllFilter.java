package com.jjerome.filters;

import com.jjerome.reflection.context.annotation.PermitAll;

import java.lang.annotation.Annotation;

public class PermitAllFilter extends AbstractSecurityFilter {

    private final Annotation[] annotations;

    private final PermitAll baseAnnotation;

    private int order;


    public PermitAllFilter(PermitAll baseAnnotation, Annotation[] annotations) {
        this.baseAnnotation = baseAnnotation;
        this.annotations = annotations;
        this.order = APPLICATION_SECURITY_PRECEDENCE;
    }

    public PermitAllFilter(Annotation baseAnnotation, Annotation[] annotations) {
        this((PermitAll) baseAnnotation, annotations);
    }

    @Override
    public void doFilter() {
        System.out.println("PermitAllFilter.doFilter");
    }

    @Override
    public String getLabel() {
        return null;
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
