package com.jjerome_test.filter;


import com.jjerome.context.annotation.WSConnectFilter;
import com.jjerome.context.annotation.WSFilter;
import com.jjerome.context.annotation.WSFiltersComponent;

@WSFiltersComponent
public class WSFilters {

    @WSConnectFilter("GoodFilter")
    public void goodFirstFilter(){
        System.out.println("WSFilters.goodFirstFilter");
    }

    @WSFilter("GoodFilter2")
    public void goodSecondFilter(){
        System.out.println("WSFilters.goodSecondFilter");
    }
}
