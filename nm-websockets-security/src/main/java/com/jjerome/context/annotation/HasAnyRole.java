package com.jjerome.context.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;


@Target({METHOD, TYPE})
@Retention(RUNTIME)
@Documented
public @interface HasAnyRole {
    @AliasFor(
            attribute = "roles"
    )
    String[] value() default "ROLE_USER";

    @AliasFor(
            attribute = "value"
    )
    String[] roles() default "ROLE_USER";
}
