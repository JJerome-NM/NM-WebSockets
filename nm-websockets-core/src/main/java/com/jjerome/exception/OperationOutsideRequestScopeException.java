package com.jjerome.exception;


public class OperationOutsideRequestScopeException extends RuntimeException {

    public OperationOutsideRequestScopeException(String message) {
        super(message);
    }
}
