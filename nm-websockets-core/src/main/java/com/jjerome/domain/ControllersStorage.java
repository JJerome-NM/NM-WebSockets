package com.jjerome.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ControllersStorage {

    Map<Class<?>, DefaultController> controllers;

    public ControllersStorage(Map<Class<?>, DefaultController> controllers){
        this.controllers = controllers;
    }

    public static ControllersStorage emptyStorage(){
        return new ControllersStorage(new HashMap<>());
    }

    public List<DefaultController> getControllersList(){
        return new ArrayList<>(controllers.values());
    }

    public DefaultController getController(Class<?> controllerClass){
        return controllers.get(controllerClass);
    }
}
