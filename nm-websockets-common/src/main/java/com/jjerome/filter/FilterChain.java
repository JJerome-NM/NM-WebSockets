package com.jjerome.filter;

import com.jjerome.core.Named;
import com.jjerome.core.Ordered;


public interface FilterChain extends Ordered, Named {

    void doFilter();

    default boolean equals(FilterChain filterChain2){
        if (this == filterChain2){
            return true;
        }
        return this.getName().equals(filterChain2.getName());
    }
}
