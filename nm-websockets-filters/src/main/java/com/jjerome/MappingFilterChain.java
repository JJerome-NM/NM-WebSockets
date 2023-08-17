package com.jjerome;


import java.util.Set;

public class MappingFilterChain implements FilterChain {

    private final Set<FilterChain> filters;

    public MappingFilterChain(Set<FilterChain> filters) {
        this.filters = filters;
    }

    @Override
    public String getLabel() {
        return this.getClass().getName();
    }

    @Override
    public int getOrder() {
        return APPLICATION_PRECEDENCE;
    }

    @Override
    public void doFilter() {
        for (FilterChain filterChain : filters){
            filterChain.doFilter();
        }
    }

    public boolean equals(FilterChain filterChain2){
        if (this == filterChain2){
            return true;
        }
        return this.getLabel().equals(filterChain2.getLabel());
    }
}
