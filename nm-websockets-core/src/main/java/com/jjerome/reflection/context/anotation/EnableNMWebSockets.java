package com.jjerome.reflection.context.anotation;


import com.jjerome.configuration.WebSocketConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Import(WebSocketConfiguration.class)
@WSComponentScan
public @interface EnableNMWebSockets {

    boolean enableSpringComponentScan() default false;

    @AliasFor(
            annotation = WSComponentScan.class,
            attribute = "basePackages"
    )
    String[] scanBasePackages() default {};

    @AliasFor(
            annotation = WSComponentScan.class,
            attribute = "baseClasses"
    )
    Class<?>[] scanBaseClasses() default {};
}
