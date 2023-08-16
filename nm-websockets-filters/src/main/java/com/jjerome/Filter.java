package com.jjerome;

import com.jjerome.context.MethodParameter;
import com.jjerome.context.Parameter;
import com.jjerome.context.annotation.WSFilter;
import com.jjerome.core.Invocable;
import com.jjerome.filter.FilterChain;
import com.jjerome.util.FilterUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiredArgsConstructor
@Getter @Setter
public class Filter implements FilterChain, Invocable {

    private final FilterComponent filterComponent;

    private final WSFilter filterAnnotation;

    private final Object springBean;

    private final Method method;

    private final MethodParameter[] methodParams;

    private final Parameter methodReturnType;


    @Override
    public Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(filterComponent.getSpringBean(), methodParameters);
    }

    @Override
    public void doFilter() {
        FilterUtil.getInstance().invokeFilterMethod(this);
    }

    @Override
    public int getOrder() {
        return filterAnnotation.order();
    }

    @Override
    public String getLabel() {
        return filterAnnotation.label();
    }

    public boolean equals(FilterChain filterChain2){
        if (this == filterChain2){
            return true;
        }
        return this.getLabel().equals(filterChain2.getLabel());
    }
}
