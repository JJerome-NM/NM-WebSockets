package com.jjerome.domain;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;


public class PrivateGlobalData {

    private Map<String, WebSocketSession> sessions = new HashMap<>();

    public PrivateGlobalData() {
    }

    public Map<String, WebSocketSession> getSessions() {
        return sessions;
    }
}
