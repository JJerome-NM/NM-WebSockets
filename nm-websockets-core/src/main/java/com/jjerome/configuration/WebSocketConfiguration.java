package com.jjerome.configuration;

import com.jjerome.context.MappingContext;
import com.jjerome.filter.ApplicationFilterChain;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.core.InitialClass;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.domain.PrivateGlobalData;
import com.jjerome.handler.RequestHandler;
import com.jjerome.handler.ResponseHandler;
import com.jjerome.handler.WebSocketHandler;
import com.jjerome.core.mapper.RequestMapper;
import com.jjerome.core.mapper.ResponseMapper;
import com.jjerome.util.BeanUtil;
import com.jjerome.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableWebSocket
@ComponentScan(basePackages = {"com.jjerome"})
public class WebSocketConfiguration {

    private static final int THREAD_POOL = Runtime.getRuntime().availableProcessors();

    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL);

    private final MappingContext mappingContext;

    private final InitialClass initialClass;

    private final MappingsStorage mappingStorage;

    private final ControllersStorage controllersStorage;

    private final PrivateGlobalData privateGlobalData;

    WebSocketConfiguration(MappingContext mappingContext,
                           BeanUtil beanUtil) {
        this.mappingContext = mappingContext;
        this.initialClass = beanUtil.findSpringBootApplicationBeanClass();

        controllersStorage = mappingContext.findAllControllers(initialClass.getClazz());
        mappingStorage = mappingContext.findAllMappings(controllersStorage.getControllersList());
        this.privateGlobalData = new PrivateGlobalData();

        System.out.println(mappingStorage.getMappings());
        System.out.println();
    }


    @Bean
    InitialClass getInitialClass(){
        return this.initialClass;
    }

    @Bean
    public RequestHandler getRequestHandler(@Autowired MappingsStorage mappingsStorage,
                                            @Autowired ResponseHandler responseHandler,
                                            @Autowired ExecutorService executorService,
                                            @Autowired RequestMapper requestMapper,
                                            @Autowired MappingUtil mappingUtil) {
        return new RequestHandler(mappingsStorage, responseHandler, executorService, requestMapper, mappingUtil);
    }

    @Bean
    public PrivateGlobalData getPrivateGlobalData(){
        return new PrivateGlobalData();
    }

    @Bean
    public ResponseHandler getResponseHandler(@Autowired PrivateGlobalData privateGlobalData,
                                              @Autowired ExecutorService executorService,
                                              @Autowired ResponseMapper responseMapper) {
        return new ResponseHandler(privateGlobalData, executorService, responseMapper);
    }

    @Bean
    public MappingsStorage getMappingStorage() {
        return mappingStorage;
    }

    @Bean
    public ControllersStorage getControllersStorage() {
        return controllersStorage;
    }

    @Bean
    public WebSocketHandler getWebSocketHandler(@Autowired RequestHandler requestHandler,
                                                @Autowired PrivateGlobalData privateGlobalData,
                                                @Autowired(required = false) ApplicationFilterChain filterChain) {
        return new WebSocketHandler(requestHandler, privateGlobalData, ApplicationFilterChain.wrapToValidDecorator(filterChain));
    }

    @Bean
    public WebSocketHandlerConfiguration getWebSocketHandlerConfiguration() {
        return new WebSocketHandlerConfiguration();
    }

    @Bean
    public ExecutorService getExecutorService() {
        return this.executorService;
    }
}
