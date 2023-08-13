package com.jjerome.filter;

import com.jjerome.core.Ordered;

public interface FilterChain extends Ordered {

    void doFilter();
}
