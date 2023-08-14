package com.jjerome.configuration;


import com.jjerome.DefaultApplicationFilterChain;
import com.jjerome.FilterChainComparator;
import com.jjerome.FiltersStorage;
import com.jjerome.context.FiltersContext;
import com.jjerome.filter.ApplicationFilterChain;
import com.jjerome.filter.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class FiltersConfiguration {

    private final FiltersStorage filtersStorage;

    FiltersConfiguration(
            FiltersContext filtersContext
    ) {
        System.out.println("------------FiltersConfiguration2-------------");


        this.filtersStorage = new FiltersStorage(filtersContext.findAllFilters());
    }

    @Bean
    public FiltersStorage filtersStorage(){
        return this.filtersStorage;
    }

    @Bean
    public ApplicationFilterChain applicationFilterChain(
            @Autowired FilterChainComparator<FilterChain> filterChainComparator,
            @Autowired FiltersStorage filtersStorage
            ) {
        var filterChain = new DefaultApplicationFilterChain(filterChainComparator, filtersStorage);

        return ApplicationFilterChain.wrapToValidDecorator(filterChain);
    }
}
