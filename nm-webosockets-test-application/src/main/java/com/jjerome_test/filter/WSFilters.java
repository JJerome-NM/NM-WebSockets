package com.jjerome_test.filter;

import com.jjerome.annotation.WSConnectFilter;

public class WSFilters {

    @WSConnectFilter("GoodFilter")
    public void gootFirstFilter(){

    }
}
