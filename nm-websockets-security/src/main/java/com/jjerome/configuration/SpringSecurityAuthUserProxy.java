package com.jjerome.configuration;

import com.jjerome.core.BasicUserDetails;

import java.lang.reflect.InvocationTargetException;

public class SpringSecurityAuthUserProxy implements BasicUserDetails {

    Object user;

    SpringSecurityAuthUserProxy(Object user){
        this.user = user;
    }

    @Override
    public String getPassword() {
        try {
            return (String) user.getClass().getMethod("getPassword").invoke(user);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
