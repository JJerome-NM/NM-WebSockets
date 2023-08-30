package com.jjerome.filters;

import com.jjerome.context.annotation.DenyAll;
import com.jjerome.context.annotation.HasRole;
import com.jjerome.exeption.AccessToMappingIsDenyException;

import java.lang.annotation.Annotation;

public class DenyAllFilter extends AbstractSecurityFilter{

    private final Annotation[] annotations;

    private final DenyAll baseAnnotation;


    public DenyAllFilter(DenyAll baseAnnotation, Annotation[] annotations) {
        this.baseAnnotation = baseAnnotation;
        this.annotations = annotations;
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
}
