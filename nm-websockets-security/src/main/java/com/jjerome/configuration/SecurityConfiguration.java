package com.jjerome.configuration;

import com.jjerome.SecurityStorage;
import com.jjerome.reflection.context.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    SecurityConfiguration(){
        Modules.SECURITY.enable();
        System.out.println("SecurityConfiguration available");
    }

    @Bean
    SecurityStorage securityStorage(
            SecurityContext securityContext
    ){
        return new SecurityStorage(securityContext.findAllAnnotationFilters());
    }
}
