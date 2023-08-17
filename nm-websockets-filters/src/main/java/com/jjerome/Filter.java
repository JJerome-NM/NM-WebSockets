package com.jjerome;

import com.jjerome.context.MethodParameter;
import com.jjerome.context.Parameter;
import com.jjerome.context.annotation.WSFilter;
import com.jjerome.core.AnnotatedComponent;
import com.jjerome.core.Invocable;
import com.jjerome.util.FilterUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiredArgsConstructor
@Builder
@Getter @Setter
public class Filter implements FilterChain, Invocable, AnnotatedComponent<WSFilter> {

    private final Annotation[] annotations;

    private final FilterComponent filterComponent;

    private final WSFilter filterAnnotation;

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

    public boolean equals(FilterChain filterChain2){
        if (this == filterChain2){
            return true;
        }
        return this.getLabel().equals(filterChain2.getLabel());
    }

    @Override
    public int getOrder() {
        return filterAnnotation.order();
    }

    @Override
    public String getLabel() {
        return filterAnnotation.label();
    }

    @Override
    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public WSFilter getComponentAnnotation() {
        return filterAnnotation;
    }

    @Override
    public Object getSpringBean() {
        return filterComponent.getSpringBean();
    }
}
