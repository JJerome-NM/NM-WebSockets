package com.jjerome.filters;

import com.jjerome.context.annotation.DenyAll;
import com.jjerome.context.annotation.PermitAll;
import com.jjerome.core.Builder;

import java.lang.annotation.Annotation;

public class PermitAllFilter extends AbstractSecurityFilter {

    private final Annotation[] annotations;

    private final PermitAll baseAnnotation;


    public PermitAllFilter(PermitAll baseAnnotation, Annotation[] annotations) {
        this.baseAnnotation = baseAnnotation;
        this.annotations = annotations;
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
}
