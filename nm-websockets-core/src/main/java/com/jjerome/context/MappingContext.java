package com.jjerome.context;

import com.jjerome.annotation.WSComponentScan;
import com.jjerome.annotation.WSController;
import com.jjerome.annotation.WSMapping;
import com.jjerome.anotation.EnableNMWebSockets;
import com.jjerome.domain.Controller;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.Mapping;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.util.LoggerUtil;
import com.jjerome.util.MergedAnnotationUtil;
import com.jjerome.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import static org.springframework.core.annotation.AnnotatedElementUtils.hasAnnotation;

@Component
@RequiredArgsConstructor
public class MappingContext {

    private final ApplicationContext context;

    private final MethodUtil methodUtil;

    private final MergedAnnotationUtil mergedAnnotationUtil;

    public ControllersStorage findAllControllers(Class<?> initialClazz) {
        LoggerUtil.disableReflectionsInfoLogs();

        Reflections reflections = new Reflections(initialClazz.getPackageName());
        EnableNMWebSockets enableAnnotation;
        WSComponentScan componentScan;
        Set<Class<?>> allControllerClasses;
        BiConsumer<String[], Class<?>[]> extractClasses;
        ComponentScan springComponentScan;
        Map<Class<?>, Controller> controllers;
        WSController controllerAnnotation;
        Annotation[] annotations;
        Object controllerSpringBean;

        if (initialClazz.isAnnotationPresent(EnableNMWebSockets.class)) {
            enableAnnotation = findMergedAnnotation(initialClazz, EnableNMWebSockets.class);

            if (enableAnnotation == null) {
                return ControllersStorage.emptyStorage();
            }

            componentScan = findMergedAnnotation(initialClazz, WSComponentScan.class);
            allControllerClasses = reflections.getTypesAnnotatedWith(WSController.class);

            extractClasses = (basePackages, baseClasses) -> {
                for (String pack : basePackages){
                    allControllerClasses.addAll(new Reflections(pack).getTypesAnnotatedWith(WSController.class));
                }
                Stream.of(baseClasses)
                        .filter(clazz -> hasAnnotation(clazz, WSController.class))
                        .forEach(allControllerClasses::add);
            };

            extractClasses.accept(componentScan.basePackages(), componentScan.baseClasses());

            if (enableAnnotation.enableSpringComponentScan()) {
                springComponentScan = findMergedAnnotation(initialClazz, ComponentScan.class);
                extractClasses.accept(springComponentScan.basePackages(), springComponentScan.basePackageClasses());
            }

            controllers = new HashMap<>();

            for (Class<?> controllerClazz : allControllerClasses){
                controllerAnnotation = findMergedAnnotation(controllerClazz, WSController.class);
                annotations = mergedAnnotationUtil.findAllAnnotations(controllerClazz);
                controllerSpringBean = context.getBean(controllerClazz);

                controllers.put(controllerClazz, new Controller(annotations, controllerAnnotation,
                        controllerClazz, controllerSpringBean));
            }
            LoggerUtil.enableReflectionsLogs();
            return new ControllersStorage(controllers);
        }
        LoggerUtil.enableReflectionsLogs();
        return ControllersStorage.emptyStorage();
    }

    public MappingsStorage findAllMappings(List<Controller> controllers) {
        LoggerUtil.disableReflectionsInfoLogs();

        Map<String, Mapping> methodMappings = new HashMap<>();
        List<Mapping> connectMappings = new ArrayList<>();
        List<Mapping> disconnectMappings = new ArrayList<>();

        WSController controllerAnnotation;
        WSMapping mappingAnnotation;
        Mapping mapping;
        String fullPath;

        for (Controller controller : controllers) {
            controllerAnnotation = findMergedAnnotation(controller.getClazz(), WSController.class);

            if (controllerAnnotation == null) {
                continue;
            }

            for (Method method : controller.getClazz().getDeclaredMethods()) {
                mappingAnnotation = findMergedAnnotation(method, WSMapping.class);

                if (mappingAnnotation == null) {
                    continue;
                }

                mapping = new Mapping(mappingAnnotation.type(), mappingAnnotation, controller.getClazz(), method,
                        methodUtil.extractMethodParameters(method), methodUtil.extractMethodReturnParameter(method));

                switch (mapping.getType()) {
                    case METHOD -> {
                        fullPath = controllerAnnotation.pathPrefix() + mapping.getMappingAnnotation().path();
                        methodMappings.put(fullPath, mapping);
                    }
                    case CONNECT -> connectMappings.add(mapping);
                    case DISCONNECT -> disconnectMappings.add(mapping);
                }
            }
        }
        LoggerUtil.enableReflectionsLogs();
        return new MappingsStorage(methodMappings, connectMappings, disconnectMappings);
    }
}
