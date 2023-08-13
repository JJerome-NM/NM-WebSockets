package com.jjerome_test.controller;

import com.jjerome.context.anotation.WSConnectMapping;
import com.jjerome.context.anotation.WSController;
import com.jjerome.context.anotation.WSMapping;
import com.jjerome.context.anotation.WSRequestBody;
import com.jjerome_test.entity.OgoClazz;
import com.jjerome.core.Request;
import com.jjerome_test.entity.Good;
import org.springframework.web.bind.annotation.PathVariable;

@WSController("/test")
public class TestWSController {

    @WSConnectMapping(responsePath = "ffg")
    public void test(@WSRequestBody Good<Good<Good<Integer, Integer>, Integer>, Integer> good){
        System.out.println(good.isGood());
    }

    @WSMapping("/2")
    public void test2(@WSRequestBody Good<Good<Good<Integer, Integer>, Integer>, Integer> good){
        System.out.println(good);
    }

    @WSMapping(path = "/{id}/good", responsePath = "/test3/response")
    public String test3(@PathVariable Request<OgoClazz> request){
        System.out.println(request.getBody().name);

        return request.getBody().name;
    }
}
