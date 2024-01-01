package com.jjerome.core;

import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;


public class Request <T> {

    private String sessionId;

    private String path;

    private HttpHeaders httpHeaders;

    private RequestHeaders headers;

    private Map<String, Object> pathVariables;

    private Map<String, Object> requestParams;

    private T body;

    public Request(String sessionId, String path, T body) {
        this.sessionId = sessionId;
        this.path = path;
        this.body = body;
        this.pathVariables = new HashMap<>();
        this.requestParams = new HashMap<>();
    }

    public Request(String sessionId, String path) {
        this(sessionId, path, null);
    }

    public Request(String sessionId) {
        this(sessionId, null, null);
    }

    public Request() {
        this(null, null, null);
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

    public Map<String, Object> getPathVariables() {
        if (pathVariables == null) {
            pathVariables = new HashMap<>();
        }
        return pathVariables;
    }

    public void setPathVariables(Map<String, Object> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public Map<String, Object> getRequestParams() {
        if (requestParams == null) {
            requestParams = new HashMap<>();
        }
        return requestParams;
    }

    public void setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public RequestHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(RequestHeaders headers) {
        this.headers = headers;
    }
}
