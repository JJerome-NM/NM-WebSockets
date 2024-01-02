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
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import static org.springframework.core.annotation.AnnotatedElementUtils.hasAnnotation;

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
        Map<String, WebSocketHandler> handlers = domainStorage.getHandlers();
        List<Controller> controllers = getAllControllers();
        Map<String, List<Controller>> handlersControllers = new HashMap<>();

        for (Controller controller : controllers) {
            String handlerPath = controller.getComponentAnnotation().handlerPath();
            if (!handlersControllers.containsKey(handlerPath)) {
                handlersControllers.put(handlerPath, new ArrayList<>());
            }
            handlersControllers.get(handlerPath).add(controller);
        }

        for (String handlerPath : handlersControllers.keySet()) {
            MappingsStorage mappingsStorage = findAllMappings(handlersControllers.get(handlerPath));
            RequestHandler requestHandler = new RequestHandler(mappingsStorage, responseHandler, executorService,
                    requestMapper, invokeUtil, sessionLocal);
            handlers.put(handlerPath, new WebSocketHandler(requestHandler, privateGlobalData));
        }
        LoggerUtil.enableReflectionsLogs();
    }

    public List<Controller> getAllControllers() {
        LoggerUtil.disableReflectionsInfoLogs();

        Reflections reflections = new Reflections(initialClass.getClazz().getPackageName());
        Set<Class<?>> allControllerClasses;

        allControllerClasses = reflections.getTypesAnnotatedWith(WSController.class);

        for (String pack : initialClass.getBasePackages()){
            allControllerClasses.addAll(new Reflections(pack).getTypesAnnotatedWith(WSController.class));
        }

        initialClass.getBaseClasses().stream()
                .filter(clazz -> hasAnnotation(clazz, WSController.class))
                .forEach(allControllerClasses::add);


        List<Controller> controllers = new ArrayList<>();
        WSController controllerAnnotation;
        Annotation[] annotations;
        Object controllerSpringBean;

        for (Class<?> controllerClazz : allControllerClasses){
            controllerAnnotation = findMergedAnnotation(controllerClazz, WSController.class);
            annotations = mergedAnnotationUtil.findAllAnnotations(controllerClazz);
            controllerSpringBean = context.getBean(controllerClazz);

            controllers.add(new DefaultController(annotations, controllerAnnotation, controllerClazz, controllerSpringBean));
        }
        LoggerUtil.enableReflectionsLogs();
        return controllers;
    }

    public MappingsStorage findAllMappings(List<Controller> controllers) {
        Map<String, Mapping> methodMappings = new HashMap<>();
        List<Mapping> connectMappings = new ArrayList<>();
        List<Mapping> disconnectMappings = new ArrayList<>();

        for (Controller controller : controllers) {
            WSController controllerAnnotation = controller.getComponentAnnotation();

            for (Method method : controller.getClazz().getDeclaredMethods()) {
                WSMapping mappingAnnotation = mergedAnnotationUtil.findAllMergedAnnotationsAndCompareArrays(method, WSMapping.class);

                if (mappingAnnotation == null) {
                    continue;
                }

                String fullPath = String.join("", controllerAnnotation.pathPrefix(), mappingAnnotation.path());
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

                switch (mapping.getType()) {
                    case METHOD -> {
                        mapping = mappingFactory.buildMapping(mapping);
                        methodMappings.put(mapping.buildFullPath(), mapping);
                    }
                    case CONNECT -> connectMappings.add(mapping);
                    case DISCONNECT -> disconnectMappings.add(mapping);
                }
            }
        }
        return new MappingsStorage(methodMappings, connectMappings, disconnectMappings);
    }
}
