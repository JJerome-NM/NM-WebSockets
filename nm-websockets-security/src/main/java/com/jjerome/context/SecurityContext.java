package com.jjerome.context;

import com.jjerome.core.Authentication;

public interface SecurityContext {

    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);
}
