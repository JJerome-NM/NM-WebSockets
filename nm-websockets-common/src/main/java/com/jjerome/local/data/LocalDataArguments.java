package com.jjerome.local.data;

import java.util.Map;
import java.util.Optional;

public interface LocalDataArguments<T> {

    Object getArgument(T localKey, String argumentName);

    Optional<Object> getOptionalArgument(T localKey, String argumentName);

    void setArgument(T localKey, String argumentName, Object data);

    Map<String, Object> getAllArguments(T localKey);

    Map<String, Optional<Object>> getAllOptionalArguments(T localKey);

    void setAllArguments(T localKey, Map<String, Object> argumentsMap);

    void addAllArguments(T localKey, Map<String, Object> argumentsMap);

    void removeAllArguments(T localKey);
}
