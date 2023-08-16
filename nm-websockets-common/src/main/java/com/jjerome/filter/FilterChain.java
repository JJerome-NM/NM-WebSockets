package com.jjerome.filter;

import com.jjerome.core.Ordered;

import java.lang.reflect.InvocationTargetException;


public interface FilterChain extends Ordered {

    void doFilter();

    String getLabel();
}
