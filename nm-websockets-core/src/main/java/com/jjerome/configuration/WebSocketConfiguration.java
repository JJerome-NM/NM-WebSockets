package com.jjerome.configuration;

import com.jjerome.context.MappingContext;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.InitialClass;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.domain.PrivateGlobalData;
import com.jjerome.handler.RequestHandler;
import com.jjerome.handler.ResponseHandler;
import com.jjerome.handler.WebSocketHandler;
import com.jjerome.mapper.RequestMapper;
import com.jjerome.mapper.ResponseMapper;
import com.jjerome.service.MappingService;
import com.jjerome.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration {

    private static final int THREAD_POOL = Runtime.getRuntime().availableProcessors();

    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL);

    private MappingContext mappingContext;

    private InitialClass initialClass;

    private MappingsStorage mappingStorage;

    private ControllersStorage controllersStorage;

    private PrivateGlobalData privateGlobalData;

    WebSocketConfiguration(MappingContext mappingContext,
                           BeanUtil beanUtil) {
        this.mappingContext = mappingContext;
        this.initialClass = beanUtil.findSpringBootApplicationBeanClass();

        controllersStorage = mappingContext.findAllControllers(initialClass.getClazz());
        mappingStorage = mappingContext.findAllMappings(controllersStorage.getControllersList());

        System.out.println(mappingStorage.getMappings());
        System.out.println();
    }


    @Bean
    public RequestHandler getRequestHandler(@Autowired MappingsStorage mappingsStorage,
                                            @Autowired ControllersStorage controllersStorage,
                                            @Autowired ResponseHandler responseHandler,
                                            @Autowired ExecutorService executorService,
                                            @Autowired RequestMapper requestMapper,
                                            @Autowired MappingService mappingService) {
        return new RequestHandler(mappingsStorage, controllersStorage, responseHandler,
                executorService, requestMapper, mappingService);
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
                                                @Autowired ResponseHandler responseHandler,
                                                @Autowired MappingsStorage mappingsStorage,
                                                @Autowired ControllersStorage controllersStorage,
                                                @Autowired PrivateGlobalData privateGlobalData) {
        return new WebSocketHandler(requestHandler, responseHandler, mappingsStorage,
                controllersStorage, privateGlobalData);
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
