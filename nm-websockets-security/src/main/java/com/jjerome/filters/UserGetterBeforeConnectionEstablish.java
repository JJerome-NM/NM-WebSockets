package com.jjerome.filters;

import com.jjerome.configuration.SupportedParentUser;
import com.jjerome.configuration.UserGetter;
import com.jjerome.context.WSSecurityContextHolder;
import com.jjerome.core.UsernamePasswordAuthenticationToken;

public class UserGetterBeforeConnectionEstablish extends AbstractSecurityFilter{

    private static final String DEFAULT_CLASS_LABEL = "UserGetterBeforeConnectionEstablish";

    private final UserGetter userGetter;

    private final SupportedParentUser parentUser;

    private int order;

    public UserGetterBeforeConnectionEstablish(UserGetter userGetter, SupportedParentUser parentUser){
        this.userGetter = userGetter;
        this.parentUser = parentUser;
        this.order = APPLICATION_SECURITY_PRECEDENCE;
    }

    @Override
    public void doFilter() {
        var user = parentUser.buildAuthUserProxy(userGetter.getUser());
        var authentication = new UsernamePasswordAuthenticationToken(user, null);

        WSSecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public String getLabel() {
        return DEFAULT_CLASS_LABEL;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }
}
