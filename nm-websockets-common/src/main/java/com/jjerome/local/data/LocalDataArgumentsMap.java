package com.jjerome.local.data;

import com.jjerome.local.data.exception.LocalDataArgumentDataCantBeNullException;
import com.jjerome.local.data.exception.LocalDataArgumentNotFoundException;
import com.jjerome.local.data.exception.LocalDataArgumentsNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;


public class LocalDataArgumentsMap implements LocalDataArguments<String> {

    private final Map<String, Map<String, Object>> argumentsMap;

    LocalDataArgumentsMap(){
        argumentsMap = new HashMap<>();
    }


    private Map<String, Object> getOriginalArgumentsMap(String sessionId){
        if (!argumentsMap.containsKey(sessionId)){
            return argumentsMap.put(sessionId, new HashMap<>());
        }

        return argumentsMap.get(sessionId);
    }

    @Override
    public Object getArgument(String sessionId, String argumentName) {
        return getOptionalArgument(sessionId, argumentName).orElseThrow(LocalDataArgumentNotFoundException::new);
    }

    @Override
    public Optional<Object> getOptionalArgument(String sessionId, String argumentName) {
        Map<String, Object> sessionDataMap = getAllArguments(sessionId);

        if (sessionDataMap.containsKey(argumentName)){
            return Optional.of(sessionDataMap.get(argumentName));
        }

        return Optional.empty();
    }

    @Override
    public void setArgument(String sessionId, String argumentName, Object data) {
        if (Objects.isNull(data)){
            throw new LocalDataArgumentDataCantBeNullException();
        }

        getOriginalArgumentsMap(sessionId).put(argumentName, data);
    }

    @Override
    public Map<String, Object> getAllArguments(String sessionId) {
        if (!argumentsMap.containsKey(sessionId)){
            throw new LocalDataArgumentsNotFoundException();
        }

        return getOriginalArgumentsMap(sessionId).entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null || entry.getKey() != null)
                .collect(toMap(Map.Entry::getKey, Optional::of));
    }

    @Override
    public Map<String, Optional<Object>> getAllOptionalArguments(String sessionId) {
        return getOriginalArgumentsMap(sessionId).entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, Optional::of));
    }

    @Override
    public void setAllArguments(String sessionId, Map<String, Object> argumentsMap) {
        getOriginalArgumentsMap(sessionId).clear();
        addAllArguments(sessionId, argumentsMap);
    }

    @Override
    public void addAllArguments(String sessionId, Map<String, Object> argumentsMap) {
        getOriginalArgumentsMap(sessionId).putAll(argumentsMap);
    }

    @Override
    public void removeAllArguments(String sessionId) {
        getOriginalArgumentsMap(sessionId).clear();
    }
}
