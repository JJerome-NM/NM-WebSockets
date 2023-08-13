package com.jjerome.context.anotation;

import com.jjerome.enums.WSMappingType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WSMapping(
        type = WSMappingType.DISCONNECT
)
public @interface WSDisconnectMapping {

    @AliasFor(
            annotation = WSMapping.class
    )
    String responsePath() default "/";

    @AliasFor(
            annotation = WSMapping.class
    )
    boolean disableReturnResponse() default false;
}
