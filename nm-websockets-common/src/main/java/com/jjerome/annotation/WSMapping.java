package com.jjerome.annotation;

import com.jjerome.enums.WSMappingType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WSMapping {

    @AliasFor(
            attribute = "path"
    )
    String value() default "/";

    String path() default "/";

    String responsePath() default "/";

    String[] filters() default {};

    WSMappingType type() default WSMappingType.METHOD;
}
