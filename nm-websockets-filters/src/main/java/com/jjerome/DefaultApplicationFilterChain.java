package com.jjerome;

import com.jjerome.core.Mapping;
import com.jjerome.filter.ApplicationFilterChain;


public class DefaultApplicationFilterChain implements ApplicationFilterChain {

    private final FiltersStorage filtersStorage;


    public DefaultApplicationFilterChain(FiltersStorage filtersStorage) {
        this.filtersStorage = filtersStorage;
    }

    @Override
    public Mapping addFilterForMapping(Mapping mapping) {
        String[] filtersLabel = mapping.getMappingAnnotation().filters();

        if (filtersLabel.length != 0){
             return new MappingFilterProxy(filtersStorage.buildMappingFilterChain(mapping), mapping);
        }
        return mapping;
    }
}
