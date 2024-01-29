package com.jjerome.core.filters;


import com.jjerome.core.Ordered;

public interface FilterChain extends Ordered {

    void doFilters();
}
