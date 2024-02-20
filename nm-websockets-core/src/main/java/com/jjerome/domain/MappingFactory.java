package com.jjerome.domain;

import com.jjerome.core.ApplicationSecurity;
import com.jjerome.core.Mapping;
import com.jjerome.core.filters.ApplicationFilterChain;
import com.jjerome.domain.strategies.mapping.collect.MappingCollectFunctionFactory;
import com.jjerome.domain.strategies.mapping.invoke.MappingInvokeFunctionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MappingFactory {

    private final ApplicationFilterChain applicationFilterChain;
    private final ApplicationSecurity applicationSecurity;
    private final MappingInvokeFunctionFactory invokeFunctionFactory;
    private final MappingCollectFunctionFactory collectFunctionFactory;


    public MappingFactory(ApplicationFilterChain applicationFilterChain,
                          @Autowired(required = false) ApplicationSecurity applicationSecurity,
                          MappingInvokeFunctionFactory invokeFunctionFactory,
                          MappingCollectFunctionFactory collectFunctionFactory) {
        this.applicationFilterChain = applicationFilterChain;
        this.applicationSecurity = applicationSecurity;
        this.invokeFunctionFactory = invokeFunctionFactory;
        this.collectFunctionFactory = collectFunctionFactory;
    }

    public Mapping buildMapping(Mapping mappingWorkpiece) {
        mappingWorkpiece = mappingWorkpiece.toBuilder()
                .requestFieldsCollectFunctions(collectFunctionFactory.buildCollectStrategies(mappingWorkpiece))
                .invokeFunction(invokeFunctionFactory.buildInvokeFunction(mappingWorkpiece))
                .build();

        return wrapMappingWithOtherModules(mappingWorkpiece);
    }

    private Mapping wrapMappingWithOtherModules(Mapping mappingWorkpiece) { // TODO mb rework this to some list...
        mappingWorkpiece = applicationFilterChain.wrapMappingWithFilter(mappingWorkpiece);

        if (applicationSecurity != null) {
            mappingWorkpiece = applicationSecurity.wrapMappingSecurity(mappingWorkpiece);
        }

        return mappingWorkpiece;
    }
}
