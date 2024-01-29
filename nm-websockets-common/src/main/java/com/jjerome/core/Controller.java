package com.jjerome.core;

import com.jjerome.reflection.context.annotation.WSController;

public interface Controller extends AnnotatedComponent<WSController> {

    Class<?> getClazz();

    String buildFullPath(Mapping mapping);

    String buildFullResponsePath(Mapping mapping);
}
