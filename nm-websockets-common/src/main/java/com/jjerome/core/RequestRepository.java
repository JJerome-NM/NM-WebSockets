package com.jjerome.core;

import org.springframework.stereotype.Component;

@Component
public class RequestRepository { // TODO: 15.08.2023 Rename this class


    // TODO: Mb create new var for request with real body
    private static final ThreadLocal<Request<UndefinedBody>> request = new ThreadLocal<>();
    public static Request<UndefinedBody> getRequest() {
        return request.get();
    }

    public static void setRequest(Request<UndefinedBody> r){
        request.set(r);
    }

    public void unload() {
        request.remove();
    }
}
