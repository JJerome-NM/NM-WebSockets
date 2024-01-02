package com.jjerome.local.data;

public interface LocalData<T, S> {

    T get();

    void set(T data);

    T get(S localDataId);

    void set(S localDataId, T data);
}
