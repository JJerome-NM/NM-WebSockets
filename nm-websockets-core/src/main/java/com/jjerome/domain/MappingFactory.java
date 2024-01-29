package com.jjerome.domain;

import com.jjerome.ApplicationSecurity;
import com.jjerome.core.Mapping;
import com.jjerome.core.MappingRequestFieldsCollectFunction;
import com.jjerome.core.Request;
import com.jjerome.core.UndefinedBody;
import com.jjerome.core.filters.ApplicationFilterChain;
import com.jjerome.domain.strategies.mapping.invoke.MappingInvokeFunctionFactory;
import com.jjerome.reflection.context.annotation.WSMapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Component
public class MappingFactory {

    private final ApplicationFilterChain applicationFilterChain;
    private final ApplicationSecurity applicationSecurity;
    private final MappingInvokeFunctionFactory functionFactory;


    public MappingFactory(ApplicationFilterChain applicationFilterChain, ApplicationSecurity applicationSecurity,
                          MappingInvokeFunctionFactory functionFactory) {
        this.applicationFilterChain = applicationFilterChain;
        this.applicationSecurity = applicationSecurity;
        this.functionFactory = functionFactory;
    }

    public Mapping buildMapping(Mapping mappingWorkpiece){
        List<BiConsumer<Request<UndefinedBody>, Mapping>> collectFunctions = new ArrayList<>();

        WSMapping wsMappingAnnotation = mappingWorkpiece.getComponentAnnotation();


        if (wsMappingAnnotation.path().contains("{") && wsMappingAnnotation.path().contains("}")) {
            collectFunctions.add(MappingRequestFieldsCollectFunction.COLLECT_REQUEST_PATH_PARAMS.get());
        }
        if (wsMappingAnnotation.path().contains("?")) {
            collectFunctions.add(MappingRequestFieldsCollectFunction.COLLECT_REQUEST_QUERY_PARAMS.get());
        }

        mappingWorkpiece = mappingWorkpiece.toBuilder()
                .requestFieldsCollectFunctions(
                        (BiConsumer<Request<UndefinedBody>, Mapping>[]) collectFunctions.toArray())
                .invokeFunction(functionFactory.buildInvokeFunction(mappingWorkpiece))
                .build();

        return wrapMappingWithOtherModules(mappingWorkpiece);
    }

    private Mapping wrapMappingWithOtherModules(Mapping mappingWorkpiece) {
        mappingWorkpiece = applicationFilterChain.wrapMappingWithFilter(mappingWorkpiece);
        mappingWorkpiece = applicationSecurity.wrapMappingSecurity(mappingWorkpiece);
        return mappingWorkpiece;
    }
}
