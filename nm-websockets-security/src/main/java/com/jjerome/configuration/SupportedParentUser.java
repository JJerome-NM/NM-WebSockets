package com.jjerome.configuration;

import com.jjerome.core.BasicUserDetails;

import java.util.function.Function;

public enum SupportedParentUser {

    SPRING_SECURITY(SpringSecurityAuthUserProxy::new);

    private final Function<Object, BasicUserDetails> buildFunction;

    SupportedParentUser(Function<Object, BasicUserDetails> buildFunction){
        this.buildFunction = buildFunction;
    }

    public BasicUserDetails buildAuthUserProxy(Object user){
        return buildFunction.apply(user);
    }
}
