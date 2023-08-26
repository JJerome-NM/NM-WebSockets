package com.jjerome;

import com.jjerome.core.Mapping;
import org.springframework.stereotype.Component;

@Component
public class MainApplicationSecurity implements ApplicationSecurity {

    private final SecurityStorage securityStorage;


    MainApplicationSecurity(SecurityStorage securityStorage){
        this.securityStorage = securityStorage;
    }


    @Override
    public Mapping wrapMappingSecurity(Mapping mapping) {
        var proxy = new SecurityMappingProxy(mapping);
        var mappingFilters = securityStorage.getFiltersForMapping(mapping.buildFullPath());

        if (mappingFilters == null){
            return mapping;
        }

        return proxy.setFilters(mappingFilters);
    }
}
