package com.jjerome.context;


public class GlobalWSSecurityContextHolderStrategy implements WSSecurityContextHolderStrategy{



    @Override
    public void clearContext() {

    }

    @Override
    public void setContext(SecurityContext securityContext) {

    }

    @Override
    public SecurityContext getContext() {
        return null;
    }

    @Override
    public SecurityContext emptyContext() {
        return null;
    }
}
