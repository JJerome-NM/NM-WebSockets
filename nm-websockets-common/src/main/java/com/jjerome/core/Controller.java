package com.jjerome.core;

import com.jjerome.context.annotation.WSController;

public interface Controller extends AnnotatedComponent<WSController> {

    Class<?> getClazz();

    String buildFullPath(Mapping mapping);
}
