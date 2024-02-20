package com.jjerome.local.data;

import com.jjerome.core.Request;
import com.jjerome.core.RequestRepository;
import com.jjerome.local.data.exception.SessionIdCanNotBeNullException;
import com.jjerome.local.data.exception.SessionRequestNotFoundException;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SessionLocal implements LocalDataArguments<String>, InitializingBean {

    private static SessionLocal INSTANCE;

    private final LocalDataArgumentsMap argumentsMap;

    public SessionLocal(){
        this.argumentsMap = new LocalDataArgumentsMap();
    }

    private String getSessionId(){
        Request<?> request = RequestRepository.getRequest();

        if (Objects.isNull(request)){
            throw new SessionRequestNotFoundException("Session local cant find request");
        }

        String sessionId = request.getSessionId();

        if (Objects.isNull(sessionId)){
            throw new SessionIdCanNotBeNullException();
        }

        return sessionId;
    }

    public Object getArgument(String argumentName) {
        return argumentsMap.getArgument(getSessionId(), argumentName);
    }

    @Override
    public Object getArgument(String localKey, String argumentName) {
        return argumentsMap.getArgument(localKey, argumentName);
    }

    public Optional<Object> getOptionalArgument(String argumentName) {
        return argumentsMap.getOptionalArgument(getSessionId(), argumentName);
    }

    @Override
    public Optional<Object> getOptionalArgument(String localKey, String argumentName) {
        return argumentsMap.getOptionalArgument(localKey, argumentName);
    }

    public void setArgument(String argumentName, Object data) {
        argumentsMap.setArgument(getSessionId(), argumentName, data);
    }

    @Override
    public void setArgument(String localKey, String argumentName, Object data) {
        argumentsMap.setArgument(localKey, argumentName, data);
    }

    public Map<String, Object> getAllArguments() {
        return argumentsMap.getAllArguments(getSessionId());
    }

    @Override
    public Map<String, Object> getAllArguments(String localKey) {
        return argumentsMap.getAllArguments(localKey);
    }

    public Map<String, Optional<Object>> getAllOptionalArguments() {
        return argumentsMap.getAllOptionalArguments(getSessionId());
    }

    @Override
    public Map<String, Optional<Object>> getAllOptionalArguments(String localKey) {
        return argumentsMap.getAllOptionalArguments(localKey);
    }

    public void setAllArguments(Map<String, Object> argumentsMap) {
        this.argumentsMap.setAllArguments(getSessionId(), argumentsMap);
    }

    @Override
    public void setAllArguments(String localKey, Map<String, Object> argumentsMap) {
        this.argumentsMap.setAllArguments(localKey, argumentsMap);
    }

    public void addAllArguments(Map<String, Object> argumentsMap) {
        this.argumentsMap.addAllArguments(getSessionId(), argumentsMap);
    }

    @Override
    public void addAllArguments(String localKey, Map<String, Object> argumentsMap) {
        this.argumentsMap.addAllArguments(localKey, argumentsMap);
    }

    public void removeAllArguments() {
        argumentsMap.removeAllArguments(getSessionId());
    }

    @Override
    public void removeAllArguments(String localKey) {
        argumentsMap.removeAllArguments(localKey);
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    public static SessionLocal getINSTANCE() {
        return INSTANCE;
    }
}
