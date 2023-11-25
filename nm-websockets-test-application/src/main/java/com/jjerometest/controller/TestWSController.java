package com.jjerometest.controller;

import com.jjerome.reflection.context.annotation.HasRole;
import com.jjerome.context.annotation.WSController;
import com.jjerome.context.annotation.WSMapping;
import org.springframework.web.bind.annotation.PathVariable;

@WSController("/test")
public class TestWSController {

    @WSMapping(
            path = "/second",
            filters = {"GoodFilter", "GoodFilter2"}
    )
    @HasRole("ADMIN")
    public Integer test2(){
        System.out.println("test2");
        return 2;
    }

    @WSMapping(path = "/{id}/good", responsePath = "/test3/response")
    public String test3(@PathVariable Integer request){

        System.out.println(request);

        return request.toString();
    }
}
