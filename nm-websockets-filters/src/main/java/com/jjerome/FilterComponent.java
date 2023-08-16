package com.jjerome;

import com.jjerome.context.annotation.WSFiltersComponent;

public class FilterComponent {

    private final WSFiltersComponent componentAnnotation;

    private final Object springBean;

    public FilterComponent(WSFiltersComponent componentAnnotation, Object springBean){
        this.componentAnnotation = componentAnnotation;
        this.springBean = springBean;
    }

    public WSFiltersComponent getComponentAnnotation() {
        return componentAnnotation;
    }

    public Object getSpringBean() {
        return springBean;
    }
}
