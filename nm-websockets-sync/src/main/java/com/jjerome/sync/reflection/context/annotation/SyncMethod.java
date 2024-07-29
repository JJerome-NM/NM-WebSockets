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
public @interface SyncMethod {

    @AliasFor(
            attribute = "changedParams"
    )
    String[] value() default {};

    /**
     * This parameter specifies which parameters can be changed after the method is called.
     * <p>This is necessary for the synchronization to work correctly and efficiently
     */
    @AliasFor(
            attribute = "value"
    )
    String[] changedParams() default {};


    // Можна попробувати через рефлекцію подивитись які змінні змінились
    boolean alwaysCheckChangedParams() default false;
}
