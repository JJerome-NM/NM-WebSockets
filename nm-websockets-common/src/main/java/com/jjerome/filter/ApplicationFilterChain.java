package com.jjerome.filter;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

public interface ApplicationFilterChain extends FilterChain {

    void doConnectFilter();

    /**
     * Accepts and implements the methods of the {@link ApplicationFilterChain} class and
     * creates a wrapper over these methods, and validates the {@link ApplicationFilterChain}
     * that was accepted before calling the real methods.
     * This class is provided to avoid problems when the user does not have a filtering module enabled.
     */
    @RequiredArgsConstructor
    class ApplicationFilterChainValidWrapper implements ApplicationFilterChain{

        private final ApplicationFilterChain filterChain;

        @Override
        public void doConnectFilter() {
            this.isFilterChainNonNull(() -> filterChain.doConnectFilter());
        }

        @Override
        public void doFilter() {
            this.isFilterChainNonNull(() -> filterChain.doFilter());
        }

        @Override
        public int getOrder() {
            return APPLICATION_PRECEDENCE;
        }

        private void isFilterChainNonNull(Runnable runFunction){
            if (Objects.nonNull(filterChain)){
                runFunction.run();
            }
        }
    }
}
