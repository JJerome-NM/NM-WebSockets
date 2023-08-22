package com.jjerome;

import com.jjerome.core.Ordered;


public interface FilterChain extends Ordered {

    void doFilter();

    String getLabel();
}
