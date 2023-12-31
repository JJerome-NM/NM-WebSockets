package com.jjerome.context;

import com.jjerome.core.Authentication;

public class SecurityContextImpl implements SecurityContext{

    private Authentication authentication;

    SecurityContextImpl(Authentication authentication){
        this.authentication = authentication;
    }

    SecurityContextImpl(){
    }

    @Override
    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}