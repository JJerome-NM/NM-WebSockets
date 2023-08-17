package com.jjerome.domain;

import com.jjerome.context.annotation.WSController;
import com.jjerome.core.Controller;
import com.jjerome.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;

@AllArgsConstructor
@Getter @Setter
public class DefaultController implements Controller {

    private Annotation[] annotations;

    private WSController controllerAnnotation;

    private Class<?> clazz;

    private Object springBean;

    @Override
    public String buildFullPath(Mapping mapping) {
        return controllerAnnotation.pathPrefix() + mapping.getComponentAnnotation().path();
    }

    @Override
    public WSController getComponentAnnotation() {
        return controllerAnnotation;
    }
}
