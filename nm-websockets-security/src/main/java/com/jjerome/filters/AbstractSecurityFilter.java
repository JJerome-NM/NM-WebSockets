package com.jjerome.filters;

import com.jjerome.core.filters.Filter;
import com.jjerome.reflection.context.AnnotatedParameter;
import com.jjerome.reflection.context.MethodParameter;


public abstract class AbstractSecurityFilter implements Filter {

    @Override
    public AnnotatedParameter[] getMethodParams() {
        return new AnnotatedParameter[0];
    }

    @Override
    public MethodParameter getMethodReturnType() {
        return null;
    }

    @Override
    public Object invoke(Object[] methodParameters) {
        return null;
    }
}
