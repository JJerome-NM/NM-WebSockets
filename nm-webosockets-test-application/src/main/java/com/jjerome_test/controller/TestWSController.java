package com.jjerome_test.controller;

import com.jjerome.annotation.WSConnectMapping;
import com.jjerome.annotation.WSController;
import com.jjerome.annotation.WSMapping;
import com.jjerome.annotation.WSRequestBody;
import com.jjerome_test.entity.OgoClazz;
import com.jjerome.domain.Request;
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
