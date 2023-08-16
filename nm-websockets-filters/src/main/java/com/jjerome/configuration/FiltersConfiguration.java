package com.jjerome.configuration;


import com.jjerome.DefaultApplicationFilterChain;
import com.jjerome.FiltersStorage;
import com.jjerome.context.FiltersContext;
import com.jjerome.filter.ApplicationFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FiltersConfiguration {

    FiltersConfiguration() {
        System.out.println("------------FiltersConfiguration2-------------");
    }

    @Bean
    public FiltersStorage filtersStorage(
            FiltersContext filtersContext
    ) {
        return new FiltersStorage(filtersContext.findAllFilters());
    }

    @Bean
    public ApplicationFilterChain applicationFilterChain(
            FiltersStorage filtersStorage
            ) {
        return ApplicationFilterChain.wrapToValidDecorator(new DefaultApplicationFilterChain(filtersStorage));
    }
}