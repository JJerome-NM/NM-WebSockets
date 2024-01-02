package com.jjerome.core;

import com.jjerome.core.filters.Filter;

import java.util.List;

public interface WSSecurityFilterChain {

    List<Filter> getFilters();
}
