package com.jjerome;

import com.jjerome.context.MethodParameter;
import com.jjerome.context.Parameter;
import com.jjerome.context.annotation.WSFilter;
import com.jjerome.core.AnnotatedComponent;
import com.jjerome.core.Invocable;
import com.jjerome.util.InvokeUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Filter implements FilterChain, Invocable, AnnotatedComponent<WSFilter> {

    private final Annotation[] annotations;

    private final FilterComponent filterComponent;

    private final WSFilter filterAnnotation;

    private final Method method;

    private final MethodParameter[] methodParams;

    private final Parameter methodReturnType;

    public Filter(Annotation[] annotations, FilterComponent filterComponent, WSFilter filterAnnotation,
                  Method method, MethodParameter[] methodParams, Parameter methodReturnType) {
        this.annotations = annotations;
        this.filterComponent = filterComponent;
        this.filterAnnotation = filterAnnotation;
        this.method = method;
        this.methodParams = methodParams;
        this.methodReturnType = methodReturnType;
    }

    @Override
    public Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(filterComponent.getSpringBean(), methodParameters);
    }

    @Override
    public void doFilter() {
        InvokeUtil.getINSTANCE().invoke(this);
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

    public FilterComponent getFilterComponent() {
        return filterComponent;
    }

    public WSFilter getFilterAnnotation() {
        return filterAnnotation;
    }

    public Method getMethod() {
        return method;
    }

    public MethodParameter[] getMethodParams() {
        return methodParams;
    }

    public Parameter getMethodReturnType() {
        return methodReturnType;
    }

    public static FilterBuilder builder(){
        return new FilterBuilder();
    }

    public static class FilterBuilder implements AnnotatedComponentBuilder<WSFilter, Filter>{

        private Annotation[] annotations;

        private FilterComponent filterComponent;

        private WSFilter componentAnnotation;

        private Method method;

        private MethodParameter[] methodParams;

        private Parameter methodReturnType;

        @Override
        public Filter build() {
            return new Filter(annotations, filterComponent, componentAnnotation, method, methodParams, methodReturnType);
        }

        @Override
        public FilterBuilder annotations(Annotation[] annotations) {
            this.annotations = annotations;
            return this;
        }

        @Override
        public FilterBuilder componentAnnotation(WSFilter componentAnnotation) {
            this.componentAnnotation = componentAnnotation;
            return this;
        }

        @Override
        public FilterBuilder springBean(Object springBean) {
            return this;
        }

        public FilterBuilder filterComponent(FilterComponent filterComponent) {
            this.filterComponent = filterComponent;
            return this;
        }

        public FilterBuilder method(Method method) {
            this.method = method;
            return this;
        }

        public FilterBuilder methodParams(MethodParameter[] methodParams) {
            this.methodParams = methodParams;
            return this;
        }

        public FilterBuilder methodReturnType(Parameter methodReturnType) {
            this.methodReturnType = methodReturnType;
            return this;
        }
    }
}
