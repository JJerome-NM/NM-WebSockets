package com.jjerome.configuration;

import com.jjerome.core.WSSecurityFilterChain;
import com.jjerome.core.enums.WSMappingType;
import com.jjerome.core.filters.Filter;

public interface WSSecurityBuilder {

    WSSecurityBuilder addUserGetterBeforeConnectionEstablish(UserGetter userGetter, SupportedParentUser originalUser);

    WSSecurityBuilder addFilter(Filter filter, WSMappingType mappingType);

    WSSecurityFilterChain build();

}
