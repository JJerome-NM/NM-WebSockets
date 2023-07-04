package com.jjerome.controller;

import com.jjerome.annotation.WSConnectMapping;
import com.jjerome.annotation.WSController;
import com.jjerome.annotation.WSMapping;
import com.jjerome.annotation.WSRequestBody;
import com.jjerome.domain.OgoClazz;
import com.jjerome.domain.Request;
import com.jjerome.entity.Good;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@WSController
public class TestWSController {

    @WSConnectMapping(responsePath = "ffg")
    public void test(@WSRequestBody Good<Good<Good<Integer>>> good, Integer lol){
        System.out.println(good.isGood());
    }

    @WSMapping("/test2")
    public void test2(@WSRequestBody OgoClazz body){
        System.out.println(body);
    }

    @WSMapping("/test3")
    public String test3(@PathVariable Request<OgoClazz> request){
        System.out.println(request.getBody().name);

        return request.getBody().name;
    }
}
