package com.jjerome.core.dto;

public class RequestDto <T> {

    private String path;

    private T body;

    public RequestDto(String path, T body) {
        this.path = path;
        this.body = body;
    }

    public RequestDto() {
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
