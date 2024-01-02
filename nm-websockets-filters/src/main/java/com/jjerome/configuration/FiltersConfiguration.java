package com.jjerome.configuration;


import com.jjerome.DefaultApplicationFilterChain;
import com.jjerome.FilterComponent;
import com.jjerome.FiltersStorage;
import com.jjerome.context.FiltersContext;
import com.jjerome.core.filters.ApplicationFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;


@Configuration
@DependsOn("securityConfiguration")
public class FiltersConfiguration {

    FiltersConfiguration() {
        Modules.FILTERS.enable();
        System.out.println("FiltersConfiguration");
    }

    @Bean
    public FiltersStorage filtersStorage(
            FiltersContext filtersContext
    ) {
        List<FilterComponent> filterComponents = filtersContext.findAllFilterComponents();
        return new FiltersStorage(filtersContext.findAllFilters(filterComponents), filterComponents);
    }

    @Bean
    public ApplicationFilterChain applicationFilterChain(
            FiltersStorage filtersStorage
            ) {
        return ApplicationFilterChain.wrapToValidDecorator(
                new DefaultApplicationFilterChain(filtersStorage));
    }
}