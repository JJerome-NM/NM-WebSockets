package com.jjerome;


import java.util.Set;

public class MappingFilterChain implements FilterChain {

    private final FilterChain[] filters;

    private final short filtersCount;


    public MappingFilterChain(Set<FilterChain> filters) {
        this.filters = filters.toArray(FilterChain[]::new);
        this.filtersCount = (short) this.filters.length;
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
        for (short i = 0; i < filtersCount; i++){
            filters[i].doFilter();
        }
    }

    public boolean equals(FilterChain filterChain2){
        if (this == filterChain2){
            return true;
        }
        return this.getLabel().equals(filterChain2.getLabel());
    }
}
