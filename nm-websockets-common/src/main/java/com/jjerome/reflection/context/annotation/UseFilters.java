package com.jjerome.reflection.context.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseFilters {

    @AliasFor(
            attribute = "filters"
    )
    String[] value() default {};

    @AliasFor(
            attribute = "value"
    )
    String[] filters() default {};
}
