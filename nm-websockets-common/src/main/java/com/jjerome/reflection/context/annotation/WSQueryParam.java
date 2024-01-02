package com.jjerome.reflection.context.annotation;

import org.springframework.core.annotation.AliasFor;

public @interface WSQueryParam {

    @AliasFor(
            attribute = "name"
    )
    String value();

    @AliasFor(
            attribute = "value"
    )
    String name();
}
