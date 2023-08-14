package com.jjerome.filter;

import com.jjerome.core.Mapping;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.function.Supplier;

public interface ApplicationFilterChain extends FilterChain {

    void doConnectFilter();

    Mapping addFilterForMapping(Mapping mapping, String... filtersNames);


    static ApplicationFilterChain wrapToValidDecorator(ApplicationFilterChain filterChain){
        return new ApplicationFilterChainValidDecorator(filterChain);
    }


    /**
     * Accepts and implements the methods of the {@link ApplicationFilterChain} class and
     * creates a wrapper over these methods, and validates the {@link ApplicationFilterChain}
     * that was accepted before calling the real methods.
     * This class is provided to avoid problems when the user does not have a filtering module enabled.
     */
    @RequiredArgsConstructor
    class ApplicationFilterChainValidDecorator implements ApplicationFilterChain{

        private final ApplicationFilterChain filterChain;

        @Override
        public void doConnectFilter() {
            isFilterChainNonNull(() -> filterChain.doConnectFilter());
        }

        @Override
        public Mapping addFilterForMapping(Mapping mapping, String... filtersNames) {
            return isFilterChainNonNull(() -> filterChain.addFilterForMapping(mapping, filtersNames));
        }

        @Override
        public void doFilter() {
            isFilterChainNonNull(() -> filterChain.doFilter());
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

        private <T> T isFilterChainNonNull(Supplier<T> getFunction){
            return Objects.nonNull(filterChain) ? getFunction.get() : null;
        }

        @Override
        public String getName() {
            return this.getClass().getName();
        }
    }
}
