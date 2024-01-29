package com.jjerome.filters;


import com.jjerome.context.WSSecurityContextHolder;
import com.jjerome.reflection.context.annotation.HasRole;

import java.lang.annotation.Annotation;

public class HasAnyRoleFilter extends AbstractSecurityFilter {

    private final Annotation[] annotations;

    private final HasRole baseAnnotation;

    private int order;


    public HasAnyRoleFilter(HasRole baseAnnotation, Annotation[] annotations) {
        this.baseAnnotation = baseAnnotation;
        this.annotations = annotations;
        this.order = APPLICATION_SECURITY_PRECEDENCE;
    }

    public HasAnyRoleFilter(Annotation baseAnnotation, Annotation[] annotations) {
        this((HasRole) baseAnnotation, annotations);
    }

    @Override
    public void doFilter() {
        System.out.println(getLabel());
        System.out.println(baseAnnotation.role());

        var test = WSSecurityContextHolder.getContext().getAuthentication();

//        System.out.println(test.getClass());
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
