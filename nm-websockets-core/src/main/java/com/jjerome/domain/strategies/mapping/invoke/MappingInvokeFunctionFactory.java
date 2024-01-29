package com.jjerome.domain.strategies.mapping.invoke;

import com.jjerome.core.Mapping;
import com.jjerome.core.MappingInvokeStrategy;
import com.jjerome.util.InvokeUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public class MappingInvokeFunctionFactory {

    private static final Map<MappingInvokeType, Class<?>> CLASSES_OF_TYPE = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(MappingInvokeType.ResponseAware, ResponseAwareMappingInvokeStrategy.class),
            new AbstractMap.SimpleEntry<>(MappingInvokeType.Simple, SimpleMappingInvokeStrategy.class)
    );
    private final ApplicationContext springContext;
    private final Map<MappingInvokeType, MappingInvokeStrategy> invokeStrategies;

    public MappingInvokeFunctionFactory(ApplicationContext springContext) {
        this.springContext = springContext;
        this.invokeStrategies = getInvokeStrategies();
    }

    private Map<MappingInvokeType, MappingInvokeStrategy> getInvokeStrategies() {
        return CLASSES_OF_TYPE.keySet()
                .stream()
                .collect(toMap(
                        Function.identity(),
                        type -> (MappingInvokeStrategy) springContext.getBean(CLASSES_OF_TYPE.get(type))
                ));
    }

    public MappingInvokeStrategy buildInvokeFunction(Mapping mapping) {
        if (mapping.returnsResponse() || !mapping.getComponentAnnotation().disableReturnResponse()) {
            return buildInvokeFunction(MappingInvokeType.ResponseAware);
        }
        return buildInvokeFunction(MappingInvokeType.Simple);
    }

    public MappingInvokeStrategy buildInvokeFunction(MappingInvokeType type) {
        return invokeStrategies.getOrDefault(type, buildDefaultInvokeStrategy());
    }

    private MappingInvokeStrategy buildDefaultInvokeStrategy() {
        return new SimpleMappingInvokeStrategy(InvokeUtil.getINSTANCE());
    }
}
