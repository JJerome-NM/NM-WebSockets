package com.jjerome.core;

public interface BasicUserDetails {

    String getPassword() throws NoSuchMethodException;

    String getUsername();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isEnabled();
}
