package com.jjerome.local.data.exception;

public class LocalDataArgumentDataCantBeNullException extends RuntimeException {

    public LocalDataArgumentDataCantBeNullException(String message) {
        super(message);
    }

    public LocalDataArgumentDataCantBeNullException() {
        this("Local data argument cant be null");
    }
}
