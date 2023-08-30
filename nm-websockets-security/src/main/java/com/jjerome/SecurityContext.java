package com.jjerome;

public interface SecurityContext {

    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);
}
