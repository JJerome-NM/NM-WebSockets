package com.jjerome.core.filters;

import com.jjerome.core.Invocable;
import com.jjerome.core.Ordered;

public interface Filter extends Invocable, Ordered {

    void doFilter();

    String getLabel();
}
