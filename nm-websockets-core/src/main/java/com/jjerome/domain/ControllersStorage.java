package com.jjerome.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ControllersStorage {

    Map<Class<?>, Controller> controllers;

    public static ControllersStorage emptyStorage(){
        return new ControllersStorage(new HashMap<>());
    }

    public List<Controller> getControllersList(){
        return new ArrayList<>(controllers.values());
    }
}
