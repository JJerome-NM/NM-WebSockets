package com.jjerome.exeption;

public class FilterChainHasFailed extends RuntimeException{

    FilterChainHasFailed(String message){
        super(message);
    }

    FilterChainHasFailed(){
        super("The filtering chain has failed");
    }
}
