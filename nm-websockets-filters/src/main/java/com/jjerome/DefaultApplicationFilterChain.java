package com.jjerome;

import com.jjerome.core.Mapping;
import com.jjerome.core.filters.ApplicationFilterChain;


public class DefaultApplicationFilterChain implements ApplicationFilterChain {

    private final FiltersStorage filtersStorage;

    public DefaultApplicationFilterChain(FiltersStorage filtersStorage) {
        this.filtersStorage = filtersStorage;
    }

    @Override
    public Mapping wrapMappingWithFilter(Mapping mapping) {
        String[] filtersLabel = mapping.getComponentAnnotation().filters();

        if (filtersLabel.length != 0){
             return new MappingFilterProxy(filtersStorage.buildMappingFilterChain(mapping), mapping);
        }
        return mapping;
    }
}
