package com.jjerome.configuration;

import com.jjerome.core.DefaulWSSecurityFilterChain;
import com.jjerome.core.WSSecurityFilterChain;
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
    public WebSocketSecurity addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public WebSocketSecurity addConnectionFilter(Filter filter) {
        connectFilters.add(filter);
        return this;
    }

    @Override
    public WebSocketSecurity addDisconnectionFilter(Filter filter) {
        disconnectFilters.add(filter);
        return this;
    }

    @Override
    public WSSecurityFilterChain build() {
        return new DefaulWSSecurityFilterChain(connectFilters);
    }
}
