package com.jjerome.core;

import java.lang.reflect.InvocationTargetException;

public interface Invocable {

    Object invoke(Object[] methodParameters) throws InvocationTargetException, IllegalAccessException;
}
