package com.jjerome.core;

import java.util.function.BiConsumer;

public enum MappingRequestFieldsCollectFunction {

    COLLECT_REQUEST_PATH_PARAMS((request, mapping) -> {

    }),
    COLLECT_REQUEST_QUERY_PARAMS((request, mapping) -> {

    });


    private final BiConsumer<Request<UndefinedBody>, Mapping> function;

    MappingRequestFieldsCollectFunction(BiConsumer<Request<UndefinedBody>, Mapping> function) {
        this.function = function;
    }

    public BiConsumer<Request<UndefinedBody>, Mapping> get() {
        return this.function;
    }
}

