package com.jjerome.configuration;


import com.jjerome.core.enums.WSMappingType;
import com.jjerome.filters.UserGetterBeforeConnectionEstablish;

public abstract class WSSecurityConfigurationBuilder implements WSSecurityBuilder{

    @Override
    public WSSecurityBuilder addUserGetterBeforeConnectionEstablish(UserGetter userGetter, SupportedParentUser originalUser) {
        return addFilter(new UserGetterBeforeConnectionEstablish(userGetter, originalUser), WSMappingType.CONNECT);
    }
}
