package com.jjerome_test.filter;


import com.jjerome.context.annotation.WSConnectFilter;

public class WSFilters {

    @WSConnectFilter("GoodFilter")
    public void goodFirstFilter(){
        System.out.println("WSFilters.goodFirstFilter");
    }
}
