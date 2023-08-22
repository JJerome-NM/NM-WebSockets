package com.jjerome.core.dto;

public class ResponseDto <T> {

    private String path;

    private T body;

    public ResponseDto(String path, T body) {
        this.path = path;
        this.body = body;
    }

    public ResponseDto() {
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
