package com.jjerome.configuration;

import com.jjerome.context.MappingContext;
import com.jjerome.filter.ApplicationFilterChain;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.core.InitialClass;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.domain.PrivateGlobalData;
import com.jjerome.handler.RequestHandler;
import com.jjerome.handler.WebSocketHandler;
import com.jjerome.util.BeanUtil;
import com.jjerome.util.MergedAnnotationUtil;
import com.jjerome.util.MethodUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    WebSocketConfiguration() {
        System.out.println("-----------WebSocketConfiguration------------");
    }

    @Bean
    public MappingContext mappingContext(
            ApplicationContext context,
            MethodUtil methodUtil,
            MergedAnnotationUtil mergedAnnotationUtil,
            @Autowired(required = false) ApplicationFilterChain applicationFilterChain
    ){
        applicationFilterChain = ApplicationFilterChain.wrapIfChainIsNull(applicationFilterChain);
        return new MappingContext(context, methodUtil, mergedAnnotationUtil, applicationFilterChain);
    }

    @Bean
    public MappingsStorage mappingsStorage(
            MappingContext mappingContext,
            ControllersStorage controllersStorage
    ) {
        return mappingContext.findAllMappings(controllersStorage.getControllersList());
    }

    @Bean
    public ControllersStorage controllersStorage(
            MappingContext mappingContext,
            InitialClass initialClass
    ) {
        return mappingContext.findAllControllers(initialClass.getClazz());
    }

    @Bean
    public InitialClass getInitialClass(
            BeanUtil beanUtil
    ) {
        return beanUtil.findSpringBootApplicationBeanClass();
    }

    @Bean
    public PrivateGlobalData getPrivateGlobalData() {
        return new PrivateGlobalData();
    }


    @Bean
    public WebSocketHandler getWebSocketHandler(
            RequestHandler requestHandler,
            PrivateGlobalData privateGlobalData
    ) {
        return new WebSocketHandler(requestHandler, privateGlobalData);
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
