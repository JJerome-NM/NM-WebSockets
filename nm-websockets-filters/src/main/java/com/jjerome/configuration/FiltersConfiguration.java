package com.jjerome.configuration;


import com.jjerome.FilterChain;
import com.jjerome.domain.InitialClass;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FiltersConfiguration {

    FiltersConfiguration(InitialClass initialClass){
        System.out.println(initialClass.getClazz());
    }

    @Bean
    FilterChain getFilterChain(){


        return new FilterChain();
    }
}
