package com.jjerome.filter;

import com.jjerome.core.Mapping;

import java.util.Objects;
import java.util.function.Supplier;

public interface ApplicationFilterChain {

    Mapping addFilterForMapping(Mapping mapping);

    static ApplicationFilterChain wrapToValidDecorator(ApplicationFilterChain filterChain){
        return new ApplicationFilterChainValidDecorator(filterChain);
    }

    static ApplicationFilterChain wrapIfChainIsNull(ApplicationFilterChain filterChain){
        return filterChain != null ? filterChain : wrapToValidDecorator(null);
    }


    /**
     * Accepts and implements the methods of the {@link ApplicationFilterChain} class and
     * creates a wrapper over these methods, and validates the {@link ApplicationFilterChain}
     * that was accepted before calling the real methods.
     * This class is provided to avoid problems when the user does not have a filtering module enabled.
     */
    class ApplicationFilterChainValidDecorator implements ApplicationFilterChain{

        private final ApplicationFilterChain filterChain;

        public ApplicationFilterChainValidDecorator(ApplicationFilterChain filterChain) {
            this.filterChain = filterChain;
        }

        @Override
        public Mapping addFilterForMapping(Mapping mapping) {
            return isFilterChainNonNullOrElse(() -> filterChain.addFilterForMapping(mapping), mapping);
        }

        private void isFilterChainNonNull(Runnable runFunction){
            if (Objects.nonNull(filterChain)){
                runFunction.run();
            }
        }

        private <T> T isFilterChainNonNullOrElse(Supplier<T> getFunction, T elseReturnThis){
            return Objects.nonNull(filterChain) ? getFunction.get() : elseReturnThis;
        }
    }
}
