package com.jjerome.core;


public interface MappingInvokeStrategy {

    void invoke(Request<UndefinedBody> request, Mapping mapping);
}
