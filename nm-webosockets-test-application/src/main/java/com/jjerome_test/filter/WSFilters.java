package com.jjerome_test.filter;


import com.jjerome.context.annotation.WSConnectFilter;
import com.jjerome.context.annotation.WSFilter;
import com.jjerome.context.annotation.WSFiltersComponent;
import com.jjerome.context.annotation.WSRequestBody;
import com.jjerome.core.Ordered;
import com.jjerome_test.entity.Good;

@WSFiltersComponent
public class WSFilters {

    @WSConnectFilter(value = "GoodFilter", order = Ordered.APPLICATION_PRECEDENCE + 1)
    public void goodFirstFilter(@WSRequestBody Good<Good<Good<Integer, Integer>, Integer>, Integer> good){
        System.out.println("WSFilters.goodFirstFilter");
        System.out.println(good.isGood());
    }

    @WSFilter(value = "GoodFilter2", order = Ordered.APPLICATION_PRECEDENCE - 1)
    public void goodSecondFilter(){
        System.out.println("WSFilters.goodSecondFilter");
    }
}
