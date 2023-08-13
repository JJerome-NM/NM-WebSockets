package com.jjerome;

import com.jjerome.domain.ApplicationFilterChain;
import com.jjerome.domain.FilterChain;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.TreeSet;

public class DefaultApplicationFilterChain implements ApplicationFilterChain {

    private final Set<FilterChain> filters;

    private final Set<FilterChain> connectFilters;


    public DefaultApplicationFilterChain(FilterChainComparator<FilterChain> filterChainComparator) {
        this.filters = new TreeSet<>(filterChainComparator);
        this.connectFilters = new TreeSet<>(filterChainComparator);
    }


    public void addFilter(FilterChain filter) {
        filters.add(filter);
    }

    public void doConnectFilter() {

        System.out.println("ApplicationFilterChain.doConnectFilter()");

        for (FilterChain filterChain : connectFilters){
            Assert.notNull(filterChain, "FilterChain cant be null");

            filterChain.doFilter();
        }
    }

    @Override
    public void doFilter() {
        System.out.println("ApplicationFilterChain.doFilter()");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
