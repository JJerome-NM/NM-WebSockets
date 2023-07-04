package com.jjerome.context;

import com.jjerome.annotation.WSComponentScan;
import com.jjerome.annotation.WSController;
import com.jjerome.annotation.WSMapping;
import com.jjerome.anotation.EnableNMWebSockets;
import com.jjerome.domain.Controller;
import com.jjerome.domain.ControllersStorage;
import com.jjerome.domain.Mapping;
import com.jjerome.domain.MappingsStorage;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import static org.springframework.core.annotation.AnnotatedElementUtils.hasAnnotation;

@Component
@RequiredArgsConstructor
public class MappingContext {

    private final ApplicationContext context;

    private final MethodUtil methodUtil;

    private final MergedAnnotationUtil mergedAnnotationUtil;

    public ControllersStorage findAllControllers(Class<?> initialClazz) {
        Reflections reflections = new Reflections(initialClazz.getPackageName());

        if (initialClazz.isAnnotationPresent(EnableNMWebSockets.class)) {
            EnableNMWebSockets enableAnnotation = findMergedAnnotation(initialClazz, EnableNMWebSockets.class);

            if (enableAnnotation == null) {
                return ControllersStorage.emptyStorage();
            }
            WSComponentScan componentScan = findMergedAnnotation(initialClazz, WSComponentScan.class);
            Set<Class<?>> allControllerClasses = reflections.getTypesAnnotatedWith(WSController.class);

            BiConsumer<String[], Class<?>[]> extractClasses = (basePackages, baseClasses) -> {
                for (String pack : basePackages){
                    allControllerClasses.addAll(new Reflections(pack).getTypesAnnotatedWith(WSController.class));
                }

                Arrays.stream(baseClasses)
                        .filter(clazz -> hasAnnotation(clazz, WSController.class))
                        .forEach(allControllerClasses::add);
            };

            extractClasses.accept(componentScan.basePackages(), componentScan.baseClasses());

            if (enableAnnotation.enableSpringComponentScan()) {
                ComponentScan springComponentScan = findMergedAnnotation(initialClazz, ComponentScan.class);
                extractClasses.accept(springComponentScan.basePackages(), springComponentScan.basePackageClasses());
            }

            Map<Class<?>, Controller> controllers = new HashMap<>();

            for (Class<?> controllerClazz : allControllerClasses){
                WSController controllerAnnotation = findMergedAnnotation(controllerClazz, WSController.class);
                Annotation[] annotations = mergedAnnotationUtil.findAllAnnotations(controllerClazz);
                Object controllerSpringBean = context.getBean(controllerClazz);

                controllers.put(controllerClazz, new Controller(annotations, controllerAnnotation,
                        controllerClazz, controllerSpringBean));
            }
            return new ControllersStorage(controllers);
        }
        return ControllersStorage.emptyStorage();
    }

    public MappingsStorage findAllMappings(List<Controller> controllers) {
        Map<String, Mapping> methodMappings = new HashMap<>();
        List<Mapping> connectMappings = new ArrayList<>();
        List<Mapping> disconnectMappings = new ArrayList<>();

        for (Controller controller : controllers) {
            WSController controllerAnnotation = findMergedAnnotation(controller.getClazz(), WSController.class);

            if (controllerAnnotation == null) {
                continue;
            }

            for (Method method : controller.getClazz().getDeclaredMethods()) {
                WSMapping mappingAnnotation = findMergedAnnotation(method, WSMapping.class);

                if (mappingAnnotation == null) {
                    continue;
                }

                Mapping mapping = new Mapping(mappingAnnotation.type(), mappingAnnotation, controller.getClazz(), method,
                        methodUtil.extractMethodParameters(method), methodUtil.extractMethodReturnParameter(method));

                switch (mapping.getType()) {
                    case METHOD -> methodMappings.put(mapping.getMappingAnnotation().path(), mapping);
                    case CONNECT -> connectMappings.add(mapping);
                    case DISCONNECT -> disconnectMappings.add(mapping);
                }
            }
        }
        return new MappingsStorage(methodMappings, connectMappings, disconnectMappings);
    }
}
