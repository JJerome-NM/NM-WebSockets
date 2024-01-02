package com.jjerome.core;

import com.jjerome.core.filters.Filter;

import java.util.List;

public class DefaulWSSecurityFilterChain implements WSSecurityFilterChain{

    private final List<Filter> filters;

    public DefaulWSSecurityFilterChain(List<Filter> filters){
        this.filters = filters;
    }

    @Override
    public List<Filter> getFilters() {
        return filters;
    }
}
