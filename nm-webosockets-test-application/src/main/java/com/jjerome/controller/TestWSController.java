package com.jjerome.controller;

import com.jjerome.annotation.WSConnectMapping;
import com.jjerome.annotation.WSController;
import com.jjerome.annotation.WSMapping;
import com.jjerome.annotation.WSRequestBody;
import com.jjerome.entity.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@WSController
public class TestWSController {

    @WSConnectMapping("ffg")
    public void test(@WSRequestBody @Autowired Good<Good<Good<Integer>>> good, Integer lol){
        System.out.println(good.isGood());
    }

    @WSMapping("fffg")
    public void test2(){

    }
}
