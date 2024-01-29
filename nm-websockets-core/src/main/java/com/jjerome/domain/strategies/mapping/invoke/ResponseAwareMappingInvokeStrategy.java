package com.jjerome.domain.strategies.mapping.invoke;

import com.jjerome.core.Mapping;
import com.jjerome.core.MappingInvokeStrategy;
import com.jjerome.core.Request;
import com.jjerome.core.Response;
import com.jjerome.core.UndefinedBody;
import com.jjerome.handler.ResponseHandler;
import com.jjerome.util.InvokeUtil;
import org.springframework.stereotype.Component;

@Component
class ResponseAwareMappingInvokeStrategy implements MappingInvokeStrategy {

    private final ResponseHandler responseHandler;
    private final InvokeUtil invokeUtil;

    ResponseAwareMappingInvokeStrategy(ResponseHandler responseHandler, InvokeUtil invokeUtil) {
        this.responseHandler = responseHandler;
        this.invokeUtil = invokeUtil;
    }

    @Override
    public void invoke(Request<UndefinedBody> request, Mapping mapping) {
        Object response = invokeUtil.invoke(mapping);

        if (!mapping.getComponentAnnotation().disableReturnResponse()) {
            String responsePath = mapping.getController().buildFullResponsePath(mapping);

            responseHandler.sendJSONMessage(request.getSessionId(), new Response<>(responsePath, response));
        }
    }
}