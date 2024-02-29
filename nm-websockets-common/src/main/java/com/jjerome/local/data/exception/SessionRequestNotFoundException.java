package com.jjerome.local.data.exception;

public class SessionRequestNotFoundException extends RuntimeException{

    public SessionRequestNotFoundException(String message){
        super(message);
    }

    public SessionRequestNotFoundException(){
        super("Session request not found");
    }
}
