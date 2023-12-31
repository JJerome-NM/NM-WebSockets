package com.jjerometest.controller;

import com.jjerome.reflection.context.anotation.WSConnectMapping;
import com.jjerome.reflection.context.annotation.HasRole;
import com.jjerome.reflection.context.annotation.WSController;
import com.jjerome.reflection.context.annotation.WSMapping;
import org.springframework.web.bind.annotation.PathVariable;

@WSController(value = "/test", handlerPath = "/socket")
public class TestWSController {

    @WSConnectMapping(responsePath = "/connect/test")
    public Integer connect(){


        return 23232;
    }

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
        // Todo Додати тип поверенення який буде включати всі можливі налаштування запиту який повертається

        System.out.println(request);

        return request.toString();
    }
}
