package com.jjerome.reflection.context;

import com.jjerome.core.Controller;
import com.jjerome.core.InitialClass;
import com.jjerome.core.Mapping;
import com.jjerome.core.mapper.RequestMapper;
import com.jjerome.domain.DefaultController;
import com.jjerome.domain.DomainStorage;
import com.jjerome.domain.MappingFactory;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.domain.PrivateGlobalData;
import com.jjerome.domain.ReadOnlyMapping;
import com.jjerome.handler.RequestHandler;
import com.jjerome.handler.ResponseHandler;
import com.jjerome.handler.WebSocketHandler;
import com.jjerome.local.data.SessionLocal;
import com.jjerome.reflection.context.annotation.WSController;
import com.jjerome.reflection.context.annotation.WSMapping;
import com.jjerome.util.InvokeUtil;
import com.jjerome.util.LoggerUtil;
import com.jjerome.util.MergedAnnotationUtil;
import com.jjerome.util.MethodUtil;
import com.jjerome.util.PathUtil;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

public class MappingContext {

    private final ApplicationContext context;
    private final MethodUtil methodUtil;
    private final MergedAnnotationUtil mergedAnnotationUtil;
    private final MappingFactory mappingFactory;
    private final InitialClass initialClass;
    private final DomainStorage domainStorage;
    private final PathUtil pathUtil;

    public MappingContext(ApplicationContext context,
                          MethodUtil methodUtil,
                          MergedAnnotationUtil mergedAnnotationUtil,
                          InitialClass initialClass,
                          MappingFactory mappingFactory,
                          DomainStorage domainStorage,
                          PathUtil pathUtil) {
        this.context = context;
        this.methodUtil = methodUtil;
        this.mergedAnnotationUtil = mergedAnnotationUtil;
        this.initialClass = initialClass;
        this.mappingFactory = mappingFactory;
        this.domainStorage = domainStorage;
        this.pathUtil = pathUtil;
    }

    public void collectWebSocketHandlers(ResponseHandler responseHandler,
                                         ExecutorService executorService,
                                         RequestMapper requestMapper,
                                         InvokeUtil invokeUtil,
                                         SessionLocal sessionLocal,
                                         PrivateGlobalData privateGlobalData) {
        LoggerUtil.disableReflectionsInfoLogs();

        Map<String, List<Controller>> splitControllers = getAllControllers()
                .stream()
                .collect(groupingBy(k -> k.getComponentAnnotation().handlerPath().equals("*") ? "SHARED" : "DEFAULT"));

        Map<String, List<Controller>> handlersControllers = splitControllers.getOrDefault("DEFAULT", Collections.emptyList())
                .stream()
                .collect(groupingBy(
                        controller -> controller.getComponentAnnotation().handlerPath(),
                        HashMap::new,
                        collectingAndThen(toList(), list -> {
                            List<Controller> combinedList = new ArrayList<>(splitControllers.getOrDefault("SHARED", Collections.emptyList()));
                            combinedList.addAll(list);
                            return combinedList;
                        })
                ));

        Map<String, WebSocketHandler> handlers = handlersControllers.entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, entry -> {
                    MappingsStorage storage = findAllMappings(entry.getValue());
                    RequestHandler requestHandler = new RequestHandler(storage, responseHandler, executorService,
                            requestMapper, invokeUtil, sessionLocal);

                    WebSocketHandler handler = new WebSocketHandler(requestHandler, privateGlobalData, entry.getKey());
                    context.getAutowireCapableBeanFactory().autowireBean(handler); // TODO mb try create bean
                    return handler;
                }));

        domainStorage.setHandlers(handlers);

        LoggerUtil.enableReflectionsLogs();
    }

    public List<Controller> getAllControllers() {
        LoggerUtil.disableReflectionsInfoLogs();

        List<Controller> controllers = context.getBeansWithAnnotation(WSController.class)
                .values().stream()
                .map(Object::getClass).map(controllerClazz -> {
                    WSController controllerAnnotation = findMergedAnnotation(controllerClazz, WSController.class);
                    Annotation[] annotations = mergedAnnotationUtil.findAllAnnotations(controllerClazz);
                    Object controllerSpringBean = context.getBean(controllerClazz);

                    return new DefaultController(annotations, controllerAnnotation, controllerClazz, controllerSpringBean);
                })
                .collect(toList());

        LoggerUtil.enableReflectionsLogs();
        return controllers;
    }

    public MappingsStorage findAllMappings(List<Controller> controllers) {
        List<Mapping> mappings = new ArrayList<>();

        for (Controller controller : controllers) {
            WSController controllerAnnotation = controller.getComponentAnnotation();

            for (Method method : controller.getClazz().getDeclaredMethods()) {
                WSMapping mappingAnnotation = mergedAnnotationUtil.findAllMergedAnnotationsAndCompareArrays(method, WSMapping.class);

                if (Objects.isNull(mappingAnnotation)) {
                    continue;
                }

                String fullPath = controllerAnnotation.pathPrefix() + mappingAnnotation.path();
                Mapping mapping = ReadOnlyMapping.builder()
                        .annotations(mergedAnnotationUtil.findAllAnnotations(method))
                        .type(mappingAnnotation.type())
                        .componentAnnotation(mappingAnnotation)
                        .controller(controller)
                        .method(method)
                        .methodParams(methodUtil.extractMethodParameters(method))
                        .methodReturnType(methodUtil.extractMethodReturnParameter(method))
                        .pathVariablesNames(pathUtil.extractPathVariables(fullPath))
                        .regexPathPattern(pathUtil.buildRegex(fullPath))
                        .build();

                mapping = mappingFactory.buildMapping(mapping);

                mappings.add(mapping);
            }
        }
        return new MappingsStorage(mappings);
    }
}
