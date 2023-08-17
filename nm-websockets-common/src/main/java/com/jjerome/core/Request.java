package com.jjerome.core;


public class Request <T> {

    private String sessionId;

    private String path;

    private T body;

    public Request(String sessionId, String path, T body) {
        this.sessionId = sessionId;
        this.path = path;
        this.body = body;
    }

    public Request(String sessionId, String path) {
        this(sessionId, path, null);
    }

    public Request() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getPath() {
        return path;
    }

    public T getBody() {
        return body;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
