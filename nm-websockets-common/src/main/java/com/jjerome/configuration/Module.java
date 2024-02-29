package com.jjerome.configuration;

public abstract class Module {

    private boolean enable = false;

    public boolean isEnable(){
        return enable;
    }

    public void enable() {
        this.enable = true;
    }
}
