package com.jjerome.exeption;

public class FilterChainNotFound extends RuntimeException{

    public FilterChainNotFound(){
        super("Filter chain not found");
    }

    public FilterChainNotFound(String message){
        super(message);
    }
}
