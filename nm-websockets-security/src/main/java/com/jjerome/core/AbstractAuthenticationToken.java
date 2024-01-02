package com.jjerome.core;

import org.springframework.util.Assert;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAuthenticationToken implements Authentication {

    private final List<GrantedAuthority> authorities;

    private Object details;

    private boolean authenticated = false;

    public AbstractAuthenticationToken(List<? extends GrantedAuthority> authorities){
        if (authorities == null){
            this.authorities = GrantedAuthority.emptyAuthority();
            return;
        }
        for (GrantedAuthority a : authorities) {
            Assert.notNull(a, "Authorities collection cannot contain any null elements");
        }
        this.authorities = Collections.unmodifiableList(new ArrayList<>(authorities));
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        if (this.getPrincipal() instanceof BasicUserDetails) {
            return ((BasicUserDetails) this.getPrincipal()).getUsername();
        }
        if (this.getPrincipal() instanceof Principal) {
            return ((Principal) this.getPrincipal()).getName();
        }
        return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractAuthenticationToken test)) {
            return false;
        }
        if (!this.authorities.equals(test.authorities)) {
            return false;
        }
        if ((this.details == null) && (test.getDetails() != null)) {
            return false;
        }
        if ((this.details != null) && (test.getDetails() == null)) {
            return false;
        }
        if ((this.details != null) && (!this.details.equals(test.getDetails()))) {
            return false;
        }
        if ((this.getCredentials() == null) && (test.getCredentials() != null)) {
            return false;
        }
        if ((this.getCredentials() != null) && !this.getCredentials().equals(test.getCredentials())) {
            return false;
        }
        if (this.getPrincipal() == null && test.getPrincipal() != null) {
            return false;
        }
        if (this.getPrincipal() != null && !this.getPrincipal().equals(test.getPrincipal())) {
            return false;
        }
        return this.isAuthenticated() == test.isAuthenticated();
    }
}
