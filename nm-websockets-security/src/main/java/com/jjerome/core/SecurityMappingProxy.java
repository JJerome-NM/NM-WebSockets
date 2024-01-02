package com.jjerome.core;

import com.jjerome.core.filters.Filter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

public class SecurityMappingProxy extends AbstractMappingProxy {

    private Filter[] filters;

    private short filtersLength;

    public SecurityMappingProxy(Mapping mapping) {
        super(mapping);
    }

    @Override
    public Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException {
        for (short i = 0; i < filtersLength; ++i){
            filters[i].doFilter();
        }
        return super.invoke(methodParameters);
    }

    public SecurityMappingProxy setFilters(Set<Filter> filters){
        return setFilters(filters.toArray(Filter[]::new));
    }

    public SecurityMappingProxy setFilters(List<Filter> filters){
        return setFilters(filters.toArray(Filter[]::new));
    }

    public SecurityMappingProxy setFilters(Filter... filters){
        this.filters = filters;
        this.filtersLength = (short) this.filters.length;
        return this;
    }
}
