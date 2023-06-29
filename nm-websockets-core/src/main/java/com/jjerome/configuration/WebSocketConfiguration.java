package com.jjerome.configuration;

import com.jjerome.context.MappingContext;
import com.jjerome.domain.Mapping;
import com.jjerome.handler.RequestHandler;
import com.jjerome.handler.ResponseHandler;
import com.jjerome.handler.WebSocketHandler;
import com.jjerome.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration {

    private MappingContext mappingContext;

    private Class<?> baseClass;

    private Map<String, Mapping> methodMappings;


    WebSocketConfiguration(MappingContext mappingContext,
                           BeanUtil beanUtil){
        this.mappingContext = mappingContext;
        this.baseClass = beanUtil.findSpringBootApplicationBeanClass();

        methodMappings = mappingContext.findAllMappings(baseClass);
    }


    @Bean
    public RequestHandler getRequestHandler(){
        return new RequestHandler();
    }

    @Bean
    public ResponseHandler getResponseHandler(){
        return new ResponseHandler();
    }

    @Bean
    public WebSocketHandler getWebSocketHandler(@Autowired RequestHandler requestHandler,
                                                @Autowired ResponseHandler responseHandler){
        return new WebSocketHandler(requestHandler, responseHandler);
    }

    @Bean
    public WebSocketHandlerConfiguration getWebSocketHandlerConfiguration(){
        return new WebSocketHandlerConfiguration();
    }
}
