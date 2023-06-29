package com.jjerome.annotation;

import com.jjerome.enums.WSFilterType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WSFilter {

    @AliasFor(
            attribute = "name"
    )
    String value() default "";

    String name() default "";

    int queueNumber() default 100;

    WSFilterType type() default WSFilterType.METHOD;
}
