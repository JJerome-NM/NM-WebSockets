package com.jjerome.domain;

import com.jjerome.ApplicationSecurity;
import com.jjerome.core.Mapping;
import com.jjerome.core.filters.ApplicationFilterChain;
import org.springframework.stereotype.Component;

@Component
public class MappingFactory {

    private final ApplicationFilterChain applicationFilterChain;

    private final ApplicationSecurity applicationSecurity;


    public MappingFactory(ApplicationFilterChain applicationFilterChain, ApplicationSecurity applicationSecurity) {
        this.applicationFilterChain = applicationFilterChain;
        this.applicationSecurity = applicationSecurity;
    }

    public Mapping buildMapping(Mapping mappingWorkpiece){
        mappingWorkpiece = applicationFilterChain.wrapMappingWithFilter(mappingWorkpiece);
        mappingWorkpiece = applicationSecurity.wrapMappingSecurity(mappingWorkpiece);
        return mappingWorkpiece;
    }
}
