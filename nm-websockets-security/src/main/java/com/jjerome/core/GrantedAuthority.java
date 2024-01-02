package com.jjerome.core;

import java.util.ArrayList;
import java.util.List;

public interface GrantedAuthority {

    String getAuthority();

    static List<GrantedAuthority> emptyAuthority(){
        return new ArrayList<>();
    }
}
