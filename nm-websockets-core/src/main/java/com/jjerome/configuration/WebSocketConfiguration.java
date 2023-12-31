package com.jjerome.configuration;

import com.jjerome.reflection.context.MappingContext;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.core.InitialClass;
import com.jjerome.domain.MappingFactory;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.domain.PrivateGlobalData;
import com.jjerome.handler.RequestHandler;
import com.jjerome.handler.WebSocketHandler;
import com.jjerome.local.data.SessionLocal;
import com.jjerome.util.BeanUtil;
import com.jjerome.util.InitUtil;
import com.jjerome.util.MergedAnnotationUtil;
import com.jjerome.util.MethodUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
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
        Modules.CORE.enable();
        System.out.println("WebSocketConfiguration");
    }

    @Bean
    @DependsOn("securityContext")
    public MappingContext mappingContext(
            ApplicationContext context,
            MethodUtil methodUtil,
            MergedAnnotationUtil mergedAnnotationUtil,
            InitialClass initialClass,
            MappingFactory mappingFactory
    ){
        return new MappingContext(context, methodUtil, mergedAnnotationUtil, initialClass, mappingFactory);
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
            MappingContext mappingContext
    ) {
        return mappingContext.getAllControllers();
    }

    @Bean
    public InitialClass getInitialClass(
            BeanUtil beanUtil,
            InitUtil initUtil
    ) {
        InitialClass initialClass = beanUtil.findSpringBootApplicationBeanClass();
        initUtil.initWSComponentScanFields(initialClass);
        return initialClass;
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

    @Bean
    public SessionLocal sessionLocal(){
        return new SessionLocal();
    }
}
