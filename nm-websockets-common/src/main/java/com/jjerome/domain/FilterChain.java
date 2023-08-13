package com.jjerome.domain;

import com.jjerome.domain.Ordered;

public interface FilterChain extends Ordered {

    void doFilter();
}
