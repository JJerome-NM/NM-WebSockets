package com.jjerome.util;

import com.jjerome.reflection.context.anotation.EnableNMWebSockets;
import com.jjerome.core.InitialClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@Component
public class InitUtil {

    public void initWSComponentScanFields(InitialClass initialClazz){
        var enableAnnotation = findMergedAnnotation(initialClazz.getClazz(), EnableNMWebSockets.class);

        Set<String> basePackages = new HashSet<>(List.of(enableAnnotation.scanBasePackages()));
        Set<Class<?>> baseClasses = new HashSet<>(List.of(enableAnnotation.scanBaseClasses()));

        if (enableAnnotation.enableSpringComponentScan()) {
            var springComponentScan = findMergedAnnotation(initialClazz.getClazz(), ComponentScan.class);
            if (springComponentScan != null){
                basePackages.addAll(List.of(springComponentScan.basePackages()));
                baseClasses.addAll(List.of(springComponentScan.basePackageClasses()));
            }
        }

        initialClazz.initWSComponentScanFields(basePackages, baseClasses, enableAnnotation.enableSpringComponentScan());
    }
}
