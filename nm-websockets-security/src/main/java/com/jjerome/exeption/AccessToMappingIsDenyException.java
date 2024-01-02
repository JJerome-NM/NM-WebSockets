package com.jjerome.exeption;

public class AccessToMappingIsDenyException extends RuntimeException{

    public AccessToMappingIsDenyException(String message){
        super(message);
    }

    public AccessToMappingIsDenyException(){
        super("Access to mapping is denied");
    }
}
