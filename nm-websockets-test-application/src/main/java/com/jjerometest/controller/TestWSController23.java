package com.jjerometest.controller;

import com.jjerome.reflection.context.annotation.WSController;
import com.jjerome.reflection.context.annotation.WSMapping;
import com.jjerome.reflection.context.annotation.WSPathVariable;
import com.jjerome.reflection.context.anotation.WSConnectMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
@WSController(value = "/room", handlerPath = "/room")
public class TestWSController23 {

    private final ApplicationContext context;


    @WSConnectMapping(responsePath = "/connect/success")
    public Integer connect() {
        context.getDisplayName();
        return 23232;
    }

    @WSMapping(
            path = "/second",
            filters = {"GoodFilter", "GoodFilter2"}
    )
    public Integer test2() {
        System.out.println("test2");
        return 2;
    }

    @WSMapping(path = "/{id}/good", responsePath = "/test3/response")
    public String test3(@WSPathVariable Integer id) {
        return id.toString();
    }

    @WSMapping(path = "/{id}/dooo", responsePath = "/test3/response")
    public String test4(@WSPathVariable String id) {

        return id.toString();
    }
}
