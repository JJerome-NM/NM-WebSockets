package com.jjerome.configuration;


import com.jjerome.filters.UserGetterBeforeConnectionEstablish;

public abstract class WSSecurityConfigurationBuilder implements WSSecurityBuilder{

    @Override
    public WSSecurityBuilder addUserGetterBeforeConnectionEstablish(UserGetter userGetter, SupportedParentUser originalUser) {
        return addConnectionFilter(new UserGetterBeforeConnectionEstablish(userGetter, originalUser));
    }
}
