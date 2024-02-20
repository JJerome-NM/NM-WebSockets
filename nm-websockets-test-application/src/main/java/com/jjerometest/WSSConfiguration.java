package com.jjerometest;

import com.jjerome.reflection.context.anotation.EnableNMWebSocketsSecurity;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableNMWebSocketsSecurity
public class WSSConfiguration {

//    @Bean
//    WSSecurityFilterChain webSocketSecurity(){
//        return new WebSocketSecurity()
//                .addUserGetterBeforeConnectionEstablish(Object::new, SupportedParentUser.SPRING_SECURITY)
//                .build();
//    }
}
