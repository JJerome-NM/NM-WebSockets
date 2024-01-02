package com.jjerometest.filter;


import com.jjerome.context.annotation.WSConnectFilter;
import com.jjerome.context.annotation.WSFilter;
import com.jjerome.context.annotation.WSFiltersComponent;
import com.jjerome.core.Ordered;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WSFiltersComponent
public class WSFilters {

    private static final Logger LOGGER = LoggerFactory.getLogger(WSFilters.class);

    @WSConnectFilter(value = "GoodFilter", order = Ordered.APPLICATION_PRECEDENCE + 1)
    public void goodFirstFilter(){
        LOGGER.info("WSFilters.goodFirstFilter");
//        throw new RuntimeException("fdgfgfg");
    }

    @WSFilter(value = "GoodFilter2", order = Ordered.APPLICATION_PRECEDENCE - 1)
    public void goodSecondFilter(){
        LOGGER.info("WSFilters.goodSecondFilter");
    }
}
