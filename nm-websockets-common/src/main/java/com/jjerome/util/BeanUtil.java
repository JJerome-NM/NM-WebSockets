package com.jjerome.util;

import com.jjerome.core.InitialClass;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class BeanUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);

    private static final String SPRING_APP_COUNT_WARN_TEXT = """
            You have more than one @SpringBootApplication in your program, this can lead to errors,
            make sure you need it and add the "scanBasePackages" parameter to your @SpringBootApplication or\s
            use the @ComponentScan annotation, it is important to choose the name of the scanned packages because\s
            the socket application works only with them
            """;

    private static final String SPRING_APP_NOT_FIND_WARN_TEXT = """
            Your application is missing the @SpringBootApplication annotation, it is used as the beginning of\s
            the application without it my web sockets will not work correctly, so please add it.
            """;

    private final ApplicationContext context;

    private final MergedAnnotationUtil mergedAnnotationUtil;

    public BeanUtil(ApplicationContext context, MergedAnnotationUtil mergedAnnotationUtil) {
        this.context = context;
        this.mergedAnnotationUtil = mergedAnnotationUtil;
    }

    public InitialClass findSpringBootApplicationBeanClass(){
        LoggerUtil.disableReflectionsInfoLogs();

        String[] beanNames = this.context.getBeanNamesForAnnotation(SpringBootApplication.class);

        if (beanNames.length > 1) {
            LOGGER.warn(SPRING_APP_COUNT_WARN_TEXT);
        } else if (beanNames.length < 1) {
            LOGGER.error(SPRING_APP_NOT_FIND_WARN_TEXT);
            return null;
        }

        Object initialClazzBean = context.getBean(beanNames[0]);

        Reflections reflections = new Reflections(initialClazzBean.getClass().getPackageName());

        Class<?> initialClazz = reflections.getTypesAnnotatedWith(SpringBootApplication.class)
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(SpringBootApplication.class))
                .findFirst()
                .orElse(null);

        Annotation[] initialClazzAnnotations = mergedAnnotationUtil.findAllAnnotations(initialClazz);

        LoggerUtil.enableReflectionsLogs();
        return new InitialClass(initialClazzAnnotations, initialClazz, initialClazzBean);
    }
}

