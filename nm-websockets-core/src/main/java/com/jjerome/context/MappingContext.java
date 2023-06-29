package com.jjerome.context;

import com.jjerome.annotation.WSComponentScan;
import com.jjerome.annotation.WSConnectMapping;
import com.jjerome.annotation.WSController;
import com.jjerome.annotation.WSMapping;
import com.jjerome.anotation.EnableNMWebSockets;
import com.jjerome.domain.Mapping;
import com.jjerome.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MappingContext {

    private final MethodUtil methodUtil;

    public List<Class<?>> findAllControllers(Class<?> baseClass){
        List<Class<?>> controllers = new ArrayList<>();

        Reflections reflection = new Reflections(baseClass.getPackageName());


        controllers.addAll(reflection.getTypesAnnotatedWith(WSController.class)
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(WSController.class))
                .collect(Collectors.toList()));

        if (baseClass.isAnnotationPresent(EnableNMWebSockets.class)){
            EnableNMWebSockets webSockets = AnnotationUtils.getAnnotation(baseClass, EnableNMWebSockets.class);
            WSComponentScan componentScan = AnnotationUtils.getAnnotation(baseClass, WSComponentScan.class);


            System.out.println(componentScan.basePackages().length);

//            if (webSockets.enableSpringComponentScan()){
//                System.out.println(webSockets.enableSpringComponentScan());
//            }
        }
        return controllers;
    }

    public Map<String, Mapping> findAllMappings(Class<?> baseClass){
        Map<String, Mapping> mappings = new HashMap<>();

        for (Class<?> controller : findAllControllers(baseClass)){
            if (!controller.isAnnotationPresent(WSController.class)){
                continue;
            }

            for (Method method : controller.getMethods()){
                if (!method.isAnnotationPresent(WSConnectMapping.class)){
                    continue;
                }
                WSMapping mappingAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, WSMapping.class);

                Mapping mapping = new Mapping(mappingAnnotation, method,
                        methodUtil.extractMethodParameters(method), methodUtil.extractMethodReturnParameter(method));

                mappings.put(mapping.getMappingAnnotation().path(), mapping);
            }
        }

        return mappings;
    }
}
