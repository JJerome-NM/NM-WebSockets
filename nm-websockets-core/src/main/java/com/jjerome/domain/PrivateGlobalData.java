package com.jjerome.domain;

import com.jjerome.core.Request;
import com.jjerome.core.RequestRepository;
import com.jjerome.exception.OperationOutsideRequestScopeException;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class PrivateGlobalData {

    private Map<String, WebSocketSession> sessions = new HashMap<>();

    public PrivateGlobalData() {
    }

    public boolean containsSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public Optional<WebSocketSession> getSession(String id) {
        return Optional.ofNullable(sessions.get(id));
    }

    public Optional<WebSocketSession> getSession() {
        Request<?> request = RequestRepository.getOptionalRequest()
                .orElseThrow(() -> new OperationOutsideRequestScopeException("It is impossible to get a session outside the request thread"));

        return Optional.ofNullable(sessions.get(request.getSessionId()));
    }

    public void addSession(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session.getId());
    }
}
