package com.jjerome.core;


public interface MappingCollectStrategy {

    void collect(Request<UndefinedBody> request, Mapping mapping);
}
