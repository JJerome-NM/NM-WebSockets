package com.jjerometest.controller;

import com.jjerome.core.Request;
import com.jjerome.reflection.context.annotation.WSController;
import com.jjerome.reflection.context.annotation.WSMapping;
import com.jjerome.reflection.context.annotation.WSPathVariable;
import com.jjerome.reflection.context.annotation.WSRequestBody;
import com.jjerome.reflection.context.anotation.WSConnectMapping;
import com.jjerometest.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
@WSController(pathPrefix = "/test", handlerPath = "/socket")
public class TestWSController {

    private final ApplicationContext context;


    @WSConnectMapping(responsePath = "/connect/test")
    public String connect() {
//        System.out.println("connect");
        context.getDisplayName();

        return "connect";
    }

    @WSMapping(
            path = "/second",
            filters = {"GoodFilter", "GoodFilter2"}
    )
    public Integer test2(){
        System.out.println("test3");
        return 2;
    }

    @WSMapping(path = "/{id}/good/{id2}/{id3}", responsePath = "/test3/response")
    public String test3(@WSPathVariable Integer id, @WSPathVariable Integer id2, @WSPathVariable Integer id3) {
        return id.toString();
    }

    @WSMapping(path = "/{id}/dooo/{name}", responsePath = "/test3/response")
    public String test4(@WSPathVariable String id, @WSPathVariable String name) {
        return name + "|" + id;
    }

    @WSMapping(path = "/ffff/{id}/dooo", responsePath = "/test3/response")
    public String test5(@WSPathVariable String id, Request<User> request, @WSRequestBody User user) {
//        System.out.println("test5");
        return id;
    }
}
