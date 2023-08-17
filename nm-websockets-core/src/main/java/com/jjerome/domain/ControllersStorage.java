package com.jjerome.domain;

import com.jjerome.core.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllersStorage {

    Map<Class<?>, Controller> controllers;

    public ControllersStorage(Map<Class<?>, Controller> controllers){
        this.controllers = controllers;
    }

    public static ControllersStorage emptyStorage(){
        return new ControllersStorage(new HashMap<>());
    }

    public List<Controller> getControllersList(){
        return new ArrayList<>(controllers.values());
    }

    public Controller getController(Class<?> controllerClass){
        return controllers.get(controllerClass);
    }

    public Map<Class<?>, Controller> getControllers() {
        return controllers;
    }
}
