package com.jjerome.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Getter
public class PrivateGlobalData {

    private Map<String, WebSocketSession> sessions = new HashMap<>();
}
