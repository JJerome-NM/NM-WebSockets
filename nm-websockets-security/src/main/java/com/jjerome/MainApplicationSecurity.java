package com.jjerome;

import com.jjerome.context.annotation.DenyAll;
import com.jjerome.context.annotation.PermitAll;
import com.jjerome.core.Mapping;
import com.jjerome.filters.DenyAllFilter;
import com.jjerome.filters.PermitAllFilter;
import org.springframework.stereotype.Component;

@Component
public class MainApplicationSecurity implements ApplicationSecurity {

    private final SecurityStorage securityStorage;


    MainApplicationSecurity(SecurityStorage securityStorage){
        this.securityStorage = securityStorage;
    }

    @Override
    public Mapping wrapMappingSecurity(Mapping mapping) {
        SecurityMappingProxy proxy = new SecurityMappingProxy(mapping);

        if (mapping.containsAnnotation(DenyAll.class)){
            return proxy.setFilters(new DenyAllFilter(mapping.getAnnotation(DenyAll.class), mapping.getAnnotations()));
        } else if (mapping.containsAnnotation(PermitAll.class)) {
            return proxy.setFilters(new PermitAllFilter(mapping.getAnnotation(PermitAll.class), mapping.getAnnotations()));
        }

        var mappingFilters = securityStorage.getFiltersForMapping(mapping.buildFullPath());

        if (mappingFilters == null){
            return mapping;
        }

        return proxy.setFilters(mappingFilters);
    }
}