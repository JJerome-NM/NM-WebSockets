package com.jjerome.filters;

import com.jjerome.reflection.context.MethodParameter;
import com.jjerome.reflection.context.Parameter;
import com.jjerome.core.filters.Filter;


public abstract class AbstractSecurityFilter implements Filter {

    @Override
    public MethodParameter[] getMethodParams() {
        return new MethodParameter[0];
    }

    @Override
    public Parameter getMethodReturnType() {
        return null;
    }

    @Override
    public Object invoke(Object[] methodParameters) {
        return null;
    }
}
