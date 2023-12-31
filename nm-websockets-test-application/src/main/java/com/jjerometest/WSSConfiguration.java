package com.jjerometest;

import com.jjerome.configuration.SupportedParentUser;
import com.jjerome.configuration.WebSocketSecurity;
import com.jjerome.reflection.context.anotation.EnableNMWebSocketsSecurity;
import com.jjerome.core.WSSecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableNMWebSocketsSecurity
public class WSSConfiguration {



    @Bean
    WSSecurityFilterChain webSocketSecurity(){
        return new WebSocketSecurity()
                .addUserGetterBeforeConnectionEstablish(Object::new, SupportedParentUser.SPRING_SECURITY)
                .build();
    }
}
