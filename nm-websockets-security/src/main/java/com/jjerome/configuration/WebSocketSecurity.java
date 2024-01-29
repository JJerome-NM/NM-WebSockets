package com.jjerome.configuration;

import com.jjerome.core.DefaulWSSecurityFilterChain;
import com.jjerome.core.WSSecurityFilterChain;
import com.jjerome.core.enums.WSMappingType;
import com.jjerome.core.filters.Filter;

import java.util.ArrayList;
import java.util.List;

public class WebSocketSecurity extends WSSecurityConfigurationBuilder {

    private List<Filter> filters = new ArrayList<>();

    private List<Filter> connectFilters = new ArrayList<>();

    private List<Filter> disconnectFilters = new ArrayList<>();

    public WebSocketSecurity(){

    }

    @Override
    public WebSocketSecurity addFilter(Filter filter, WSMappingType type) {
        filters.add(filter);
        return this;
    }

    @Override
    public WSSecurityFilterChain build() {
        return new DefaulWSSecurityFilterChain(connectFilters);
    }
}
