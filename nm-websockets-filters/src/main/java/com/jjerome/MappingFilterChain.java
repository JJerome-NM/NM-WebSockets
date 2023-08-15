package com.jjerome;

import com.jjerome.filter.FilterChain;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class MappingFilterChain implements FilterChain {

    private final Set<FilterChain> filters;


    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public int getOrder() {
        return APPLICATION_PRECEDENCE;
    }

    @Override
    public void doFilter() {
        System.out.println(this.getClass().getName());

        for (FilterChain filterChain : filters){
            filterChain.doFilter();
        }
    }
}
