package com.jjerome.configuration;

import com.jjerome.context.MappingContext;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.InitialClass;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.handler.RequestHandler;
import com.jjerome.handler.ResponseHandler;
import com.jjerome.handler.WebSocketHandler;
import com.jjerome.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration {

    private MappingContext mappingContext;

    private InitialClass initialClass;

    private MappingsStorage mappingStorage;

    private ControllersStorage controllersStorage;


    WebSocketConfiguration(MappingContext mappingContext,
                           BeanUtil beanUtil){
        this.mappingContext = mappingContext;
        this.initialClass = beanUtil.findSpringBootApplicationBeanClass();

        controllersStorage = mappingContext.findAllControllers(initialClass.getClazz());
        mappingStorage = mappingContext.findAllMappings(controllersStorage.getControllersList());

        System.out.println(mappingStorage.getMappings());
        System.out.println();
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
