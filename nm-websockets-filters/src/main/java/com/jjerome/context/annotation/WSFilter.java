package com.jjerome.context.annotation;

import com.jjerome.core.Ordered;
import com.jjerome.context.enums.WSFilterType;
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
            attribute = "label"
    )
    String value() default "";

    String label() default "";

    int order() default Ordered.APPLICATION_PRECEDENCE;

    WSFilterType type() default WSFilterType.METHOD;
}
