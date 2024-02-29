package com.jjerome.domain.strategies.mapping.collect;

import com.jjerome.core.Mapping;
import com.jjerome.core.MappingCollectStrategy;
import com.jjerome.core.MappingCollectType;
import com.jjerome.reflection.context.annotation.WSMapping;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;


@Component
public class MappingCollectFunctionFactory {

    private static final Map<MappingCollectType, Class<?>> CLASSES_OF_TYPE = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(MappingCollectType.REQUEST_PATH_VARIABLE, RequestPathVariableCollectStrategy.class),
            new AbstractMap.SimpleEntry<>(MappingCollectType.REQUEST_QUERY_PARAMS, RequestQueryParamsCollectStrategy.class)
    );
    private final ApplicationContext springContext;
    private final Map<MappingCollectType, MappingCollectStrategy> collectStrategies;

    public MappingCollectFunctionFactory(ApplicationContext springContext) {
        this.springContext = springContext;
        this.collectStrategies = getCollectStrategies();
    }

    private Map<MappingCollectType, MappingCollectStrategy> getCollectStrategies() {
        return CLASSES_OF_TYPE.keySet()
                .stream()
                .collect(toMap(
                        Function.identity(),
                        type -> (MappingCollectStrategy) springContext.getBean(CLASSES_OF_TYPE.get(type))
                ));
    }

    public MappingCollectStrategy[] buildCollectStrategies(Mapping mapping) {
        WSMapping wsMappingAnnotation = mapping.getComponentAnnotation();
        List<MappingCollectType> collectTypes = new ArrayList<>();


        if (ArrayUtils.isNotEmpty(mapping.getPathVariablesNames())) {
            collectTypes.add(MappingCollectType.REQUEST_PATH_VARIABLE);
        }
        if (wsMappingAnnotation.path().contains("?")) {
            collectTypes.add(MappingCollectType.REQUEST_QUERY_PARAMS);
        }

        return buildCollectStrategies(collectTypes.toArray(MappingCollectType[]::new));
    }

    public MappingCollectStrategy[] buildCollectStrategies(MappingCollectType... collectTypes) {
        return Arrays.stream(collectTypes)
                .map(collectStrategies::get)
                .toArray(MappingCollectStrategy[]::new);
    }
}
