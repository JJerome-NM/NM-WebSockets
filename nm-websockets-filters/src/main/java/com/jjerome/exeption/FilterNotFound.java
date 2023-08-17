package com.jjerome.exeption;

public class FilterNotFound extends RuntimeException{

    public FilterNotFound(){
        super("Filter chain not found");
    }

    public FilterNotFound(String message){
        super(message);
    }
}
