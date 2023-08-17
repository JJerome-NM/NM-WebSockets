package com.jjerome;

import com.jjerome.core.Ordered;

import java.lang.reflect.InvocationTargetException;


public interface FilterChain extends Ordered {

    void doFilter();

    String getLabel();
}
