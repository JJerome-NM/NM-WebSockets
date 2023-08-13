package com.jjerome.configuration;


import com.jjerome.DefaultApplicationFilterChain;
import com.jjerome.FilterChainComparator;
import com.jjerome.domain.ApplicationFilterChain;
import com.jjerome.domain.InitialClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiltersConfiguration {

    private final InitialClass initialClass;

    FiltersConfiguration(InitialClass initialClass) {
        System.out.println("------------FiltersConfiguration2-------------");

        System.out.println(initialClass.getClazz());

        this.initialClass = initialClass;
    }

    @Bean
    public ApplicationFilterChain applicationFilterChain(
            @Autowired(required = false) FilterChainComparator filterChainComparator
    ) {
        return new DefaultApplicationFilterChain(filterChainComparator);
    }
}
