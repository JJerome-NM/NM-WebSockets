package com.jjerome.exception;

public class HandlerNotFoundException extends RuntimeException {


    public HandlerNotFoundException() {
        super("Handler not found");
    }

    public HandlerNotFoundException(String message) {
        super(message);
    }
}
