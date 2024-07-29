package com.jjerometest.controller;

import com.jjerome.reflection.context.annotation.WSController;
import com.jjerome.reflection.context.annotation.WSMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
@WSController(handlerPath = "/socket")
public class TestWSController11 {

    private final ApplicationContext context;

    @WSMapping(filters = {"GoodFilter", "GoodFilter2"})
    public Integer test2() {
//        System.out.println("test2");
        return 2;
    }

//    @WSMapping(path = "/{id}/good/{id2}/{id3}", responsePath = "/test3/response")
//    public String test3(@WSPathVariable Integer id, @WSPathVariable Integer id2, @WSPathVariable Integer id3) {
//        return id.toString();
//    }
//
//    @WSMapping(path = "/ffff/{id}/dooo", responsePath = "/test3/response")
//    public String test5(@WSPathVariable String id, Request<User> request, @WSRequestBody User user) {
//        return id;
//    }
}
