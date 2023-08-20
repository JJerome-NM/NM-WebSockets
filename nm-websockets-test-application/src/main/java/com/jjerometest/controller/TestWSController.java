package com.jjerometest.controller;

import com.jjerome.context.anotation.WSConnectMapping;
import com.jjerome.context.annotation.WSController;
import com.jjerome.context.annotation.WSMapping;
import com.jjerome.context.annotation.WSRequestBody;
import com.jjerometest.entity.Good;
import org.springframework.web.bind.annotation.PathVariable;

@WSController("/test")
public class TestWSController {

    @WSConnectMapping(
            responsePath = "/connected",
            filters = {"GoodFilter", "GoodFilter2"}
    )
    public void test(@WSRequestBody Good<Good<Good<Integer, Integer>, Integer>, Integer> good){
        System.out.println(good.isGood());
    }

    @WSMapping(
            path = "/second",
            filters = {"GoodFilter", "GoodFilter2"}
    )
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
