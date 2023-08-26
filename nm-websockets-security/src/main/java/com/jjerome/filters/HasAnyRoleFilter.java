package com.jjerome.filters;


import com.jjerome.context.annotation.HasRole;

import java.lang.annotation.Annotation;

public class HasAnyRoleFilter extends AbstractSecurityFilter {

    private final Annotation[] annotations;

    private final HasRole baseAnnotation;


    public HasAnyRoleFilter(HasRole baseAnnotation, Annotation[] annotations) {
        this.baseAnnotation = baseAnnotation;
        this.annotations = annotations;
    }

    @Override
    public void doFilter() {
        System.out.println(getLabel());
        System.out.println(baseAnnotation.role());
    }

    @Override
    public String getLabel() {
        return this.getClass().getSimpleName();
    }
}
