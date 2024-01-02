package com.jjerome.local.data.exception;

public class SessionIdCanNotBeNullException extends RuntimeException{

    public SessionIdCanNotBeNullException(String message){
        super(message);
    }

    public SessionIdCanNotBeNullException(){
        this("Session id can not be null");
    }
}
