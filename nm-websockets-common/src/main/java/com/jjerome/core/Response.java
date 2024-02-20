package com.jjerome.core;

public class Response <T>{

    private String path;
    private T body;

    public Response(String path, T body) {
        this.path = path;
        this.body = body;
    }

    public Response() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
