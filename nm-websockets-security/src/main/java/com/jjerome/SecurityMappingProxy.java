package com.jjerome;

import com.jjerome.core.Mapping;
import com.jjerome.core.filters.Filter;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class SecurityMappingProxy extends AbstractMappingProxy {

    private Filter[] filters;

    private short filtersLength;

    protected SecurityMappingProxy(Mapping mapping) {
        super(mapping);
    }

    @Override
    public Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException {
        for (short i = 0; i < filtersLength; i++){
            filters[i].doFilter();
        }

        return super.invoke(methodParameters);
    }

    public SecurityMappingProxy setFilters(Set<Filter> filters){
        this.filters = filters.toArray(Filter[]::new);
        this.filtersLength = (short) this.filters.length;
        return this;
    }
}
