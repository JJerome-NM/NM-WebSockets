package com.jjerome.context;

import com.jjerome.context.annotation.WSController;
import com.jjerome.context.annotation.WSMapping;
import com.jjerome.core.Controller;
import com.jjerome.core.InitialClass;
import com.jjerome.core.Mapping;
import com.jjerome.domain.DefaultController;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.DefaultMapping;
import com.jjerome.domain.MappingsStorage;
import com.jjerome.filter.ApplicationFilterChain;
import com.jjerome.util.LoggerUtil;
import com.jjerome.util.MergedAnnotationUtil;
import com.jjerome.util.MethodUtil;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import static org.springframework.core.annotation.AnnotatedElementUtils.hasAnnotation;

public class MappingContext {

    private final ApplicationContext context;

    private final MethodUtil methodUtil;

    private final MergedAnnotationUtil mergedAnnotationUtil;

    private final ApplicationFilterChain applicationFilterChain;

    private final InitialClass initialClass;

    public MappingContext(ApplicationContext context,
                          MethodUtil methodUtil,
                          MergedAnnotationUtil mergedAnnotationUtil,
                          InitialClass initialClass,
                          @Autowired(required = false) ApplicationFilterChain applicationFilterChain) {
        this.context = context;
        this.methodUtil = methodUtil;
        this.mergedAnnotationUtil = mergedAnnotationUtil;
        this.initialClass = initialClass;
        this.applicationFilterChain = applicationFilterChain;
    }

    public ControllersStorage getAllControllers() {
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


        Map<Class<?>, Controller> controllers = new HashMap<>();
        WSController controllerAnnotation;
        Annotation[] annotations;
        Object controllerSpringBean;

        for (Class<?> controllerClazz : allControllerClasses){
            controllerAnnotation = findMergedAnnotation(controllerClazz, WSController.class);
            annotations = mergedAnnotationUtil.findAllAnnotations(controllerClazz);
            controllerSpringBean = context.getBean(controllerClazz);

            controllers.put(controllerClazz, new DefaultController(annotations, controllerAnnotation,
                    controllerClazz, controllerSpringBean));
        }
        LoggerUtil.enableReflectionsLogs();
        return new ControllersStorage(controllers);
    }

    public MappingsStorage findAllMappings(List<Controller> controllers) {
        Map<String, Mapping> methodMappings = new HashMap<>();
        List<Mapping> connectMappings = new ArrayList<>();
        List<Mapping> disconnectMappings = new ArrayList<>();

        WSMapping mappingAnnotation;
        Mapping mapping;

        for (Controller controller : controllers) {
            for (Method method : controller.getClazz().getDeclaredMethods()) {
                mappingAnnotation = findMergedAnnotation(method, WSMapping.class);

                if (mappingAnnotation == null) {
                    continue;
                }

                mapping = DefaultMapping.builder()
                        .annotations(mergedAnnotationUtil.findAllAnnotations(method))
                        .type(mappingAnnotation.type())
                        .mappingAnnotation(mappingAnnotation)
                        .controller(controller)
                        .method(method)
                        .methodParams(methodUtil.extractMethodParameters(method))
                        .methodReturnType(methodUtil.extractMethodReturnParameter(method))
                        .build();

                switch (mapping.getType()) {
                    case METHOD -> {
                        mapping = applicationFilterChain.addFilterForMapping(mapping);
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
