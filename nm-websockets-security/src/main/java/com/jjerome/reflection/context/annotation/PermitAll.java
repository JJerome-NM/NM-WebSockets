package com.jjerome.reflection.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;


@Target({METHOD, TYPE})
@Retention(RUNTIME)
@Documented
public @interface PermitAll{
}
