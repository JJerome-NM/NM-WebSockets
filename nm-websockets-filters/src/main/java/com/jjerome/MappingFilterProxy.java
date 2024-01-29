package com.jjerome;

import com.jjerome.core.AbstractMappingProxy;
import com.jjerome.core.Mapping;
import com.jjerome.core.filters.FilterChain;

import java.lang.reflect.InvocationTargetException;

public class MappingFilterProxy extends AbstractMappingProxy {

    private final FilterChain filterChain;

    MappingFilterProxy(FilterChain filterChain, Mapping realMapping){
        super(realMapping);
        this.filterChain = filterChain;
    }

    @Override
    public Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException {
        filterChain.doFilters();
        return super.invoke(methodParameters);
    }
}
