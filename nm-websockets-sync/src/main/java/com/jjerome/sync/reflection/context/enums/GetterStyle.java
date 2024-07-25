package com.jjerome.sync.reflection.context.enums;


public enum GetterStyle {

    /**
     * {@link GetterStyle GetterStyle.DEFAULT} this type uses the standard integrity idea
     * <pre>
     * It looks like:
     * - {@code getVariableName()}
     * </pre>
     */
    DEFAULT,

    /**
     * {@link GetterStyle GetterStyle.DEFAULT} this type uses the standard integrity idea
     * <pre>
     * It looks like:
     * - {@code variableName()}
     * </pre>
     */
    RECORD
}
