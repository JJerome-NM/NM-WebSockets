package com.jjerome;


import com.jjerome.core.filters.Filter;
import com.jjerome.core.filters.FilterChain;

import java.util.Set;

public class MappingFilterChain implements FilterChain {

    private final Filter[] filters;

    private final short filtersCount;

    private int order;

    public MappingFilterChain(Set<Filter> filters) {
        this.filters = filters.toArray(Filter[]::new);
        this.filtersCount = (short) this.filters.length;
        this.order = APPLICATION_PRECEDENCE;
    }

    @Override
    public void doFilters() {
        for (short i = 0; i < filtersCount; i++){
            filters[i].doFilter();
        }
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
