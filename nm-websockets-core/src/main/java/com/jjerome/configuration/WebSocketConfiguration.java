package com.jjerome.configuration;

import com.jjerome.core.InitialClass;
import com.jjerome.core.mapper.RequestMapper;
import com.jjerome.domain.DomainStorage;
import com.jjerome.domain.MappingFactory;
import com.jjerome.domain.PrivateGlobalData;
import com.jjerome.handler.ResponseHandler;
import com.jjerome.handler.WebSocketHandler;
import com.jjerome.local.data.SessionLocal;
import com.jjerome.reflection.context.MappingContext;
import com.jjerome.util.BeanUtil;
import com.jjerome.util.InitUtil;
import com.jjerome.util.InvokeUtil;
import com.jjerome.util.MergedAnnotationUtil;
import com.jjerome.util.MethodUtil;
import com.jjerome.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
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
            MappingFactory mappingFactory,
            DomainStorage domainStorage,
            PathUtil pathUtil
    ) {
        return new MappingContext(context, methodUtil, mergedAnnotationUtil, initialClass, mappingFactory, domainStorage, pathUtil);
    }

    @Bean
    public DomainStorage domainStorage() {          ////
        return new DomainStorage();
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
    public WebSocketHandlerConfiguration getWebSocketHandlerConfiguration(
            ResponseHandler responseHandler,
            ExecutorService executorService,
            RequestMapper requestMapper,
            InvokeUtil invokeUtil,
            SessionLocal sessionLocal,
            PrivateGlobalData privateGlobalData,
            MappingContext mappingContext) {
        mappingContext.collectWebSocketHandlers(responseHandler, executorService, requestMapper, invokeUtil,
                sessionLocal, privateGlobalData);
        var handlersConfiguration = new WebSocketHandlerConfiguration();

        LOGGER.info("NM-WebSockets successfully started");
        LOGGER.error("Happy hacking😘");

        return handlersConfiguration;
    }

    @Bean
    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    @Bean
    public SessionLocal sessionLocal() {
        return new SessionLocal();
    }
}
