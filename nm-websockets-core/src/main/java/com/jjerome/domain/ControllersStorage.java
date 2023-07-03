package com.jjerome.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
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
}
