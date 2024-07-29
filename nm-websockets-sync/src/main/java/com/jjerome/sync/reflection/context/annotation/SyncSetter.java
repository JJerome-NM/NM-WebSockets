package com.jjerome.sync.reflection.context.annotation;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is useful for {@link SyncMe @SyncMe}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SyncSetter {

    @AliasFor(
            attribute = "parameter"
    )
    String value();

    /**
     * Specifies the setter of a particular parameter, this may not be necessary if you use regular setters.
     * <pre>
     * Such as:
     * - setParameter(Type value)
     * </pre>
     */
    @AliasFor(
            attribute = "value"
    )
    String parameter();
}
