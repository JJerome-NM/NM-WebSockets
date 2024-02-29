package com.jjerome.domain.strategies.mapping.invoke;

import com.jjerome.core.Mapping;
import com.jjerome.core.MappingInvokeStrategy;
import com.jjerome.core.Request;
import com.jjerome.core.UndefinedBody;
import com.jjerome.util.InvokeUtil;
import org.springframework.stereotype.Component;


@Component
class SimpleMappingInvokeStrategy implements MappingInvokeStrategy {

    private final InvokeUtil invokeUtil;

    SimpleMappingInvokeStrategy(InvokeUtil invokeUtil) {
        this.invokeUtil = invokeUtil;
    }

    @Override
    public void invoke(Request<UndefinedBody> request, Mapping mapping) {
        invokeUtil.invoke(mapping);
    }
}
