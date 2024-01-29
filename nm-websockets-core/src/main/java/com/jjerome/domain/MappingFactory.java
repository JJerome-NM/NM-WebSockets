package com.jjerome.domain;

import com.jjerome.ApplicationSecurity;
import com.jjerome.core.Mapping;
import com.jjerome.core.filters.ApplicationFilterChain;
import com.jjerome.domain.strategies.mapping.collect.MappingCollectFunctionFactory;
import com.jjerome.domain.strategies.mapping.invoke.MappingInvokeFunctionFactory;
import org.springframework.stereotype.Component;

@Component
public class MappingFactory {

    private final ApplicationFilterChain applicationFilterChain;
    private final ApplicationSecurity applicationSecurity;
    private final MappingInvokeFunctionFactory invokeFunctionFactory;
    private final MappingCollectFunctionFactory collectFunctionFactory;


    public MappingFactory(ApplicationFilterChain applicationFilterChain,
                          ApplicationSecurity applicationSecurity,
                          MappingInvokeFunctionFactory invokeFunctionFactory,
                          MappingCollectFunctionFactory collectFunctionFactory) {
        this.applicationFilterChain = applicationFilterChain;
        this.applicationSecurity = applicationSecurity;
        this.invokeFunctionFactory = invokeFunctionFactory;
        this.collectFunctionFactory = collectFunctionFactory;
    }

    public Mapping buildMapping(Mapping mappingWorkpiece) {
        mappingWorkpiece = mappingWorkpiece.toBuilder()
                .requestFieldsCollectFunctions(collectFunctionFactory.buildCollectStrategies(mappingWorkpiece)) // Error here
                .invokeFunction(invokeFunctionFactory.buildInvokeFunction(mappingWorkpiece))
                .build();

        return wrapMappingWithOtherModules(mappingWorkpiece);
    }

    private Mapping wrapMappingWithOtherModules(Mapping mappingWorkpiece) {
        mappingWorkpiece = applicationFilterChain.wrapMappingWithFilter(mappingWorkpiece);
        mappingWorkpiece = applicationSecurity.wrapMappingSecurity(mappingWorkpiece);
        return mappingWorkpiece;
    }
}
