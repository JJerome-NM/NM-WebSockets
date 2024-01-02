package com.jjerome.context;

import java.util.function.Supplier;

public interface WSSecurityContextHolderStrategy {

    void clearContext();

    void setContext(SecurityContext securityContext);

    default void setDeferredContext(Supplier<SecurityContext> deferredContext) {
        this.setContext(deferredContext.get());
    }

    default Supplier<SecurityContext> getDeferredContext() {
        return this::getContext;
    }

    SecurityContext getContext();

    SecurityContext emptyContext();
}
