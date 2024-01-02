package com.jjerome.reflection.context.annotation;

import com.jjerome.core.enums.WSMappingType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@UseFilters
public @interface WSMapping {

    @AliasFor(
            attribute = "path"
    )
    String value() default "/";

    @AliasFor(
            attribute = "value"
    )
    String path() default "/";

    String responsePath() default "/";

    @AliasFor(
            annotation = UseFilters.class,
            attribute = "filters"
    )
    String[] filters() default {};

    boolean disableReturnResponse() default false;

    WSMappingType type() default WSMappingType.METHOD;
}
