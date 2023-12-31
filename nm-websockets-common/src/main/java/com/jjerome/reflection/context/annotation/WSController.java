package com.jjerome.reflection.context.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface WSController {

    @AliasFor(
            attribute = "pathPrefix"
    )
    String value() default "";

    @AliasFor(
            attribute = "value"
    )
    String pathPrefix() default "";

    String responsePathPrefix() default "";

    String handlerPath() default "/";
}
