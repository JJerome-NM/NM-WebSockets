package com.jjerome.local.data.exception;

public class LocalDataArgumentsNotFoundException extends RuntimeException{

    public LocalDataArgumentsNotFoundException(String message){
        super(message);
    }

    public LocalDataArgumentsNotFoundException(){
        this("Local data arguments not found exception");
    }
}
