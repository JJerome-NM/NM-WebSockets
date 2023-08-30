package com.jjerome;

public interface BasicAuthUser {

    String getPassword();

    String getUsername();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isEnabled();
}
