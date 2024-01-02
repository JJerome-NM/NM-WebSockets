package com.jjerome.local.data.exception;

public class LocalDataArgumentNotFoundException extends RuntimeException {

    public LocalDataArgumentNotFoundException(String message) {
        super(message);
    }

    public LocalDataArgumentNotFoundException() {
        this("Local data argument not found exception");
    }
}
