package com.jjerome.configuration;

public final class Modules {

    public static final Core CORE = new Core();

    public static final Filters FILTERS = new Filters();

    public static final Security SECURITY = new Security();

    private Modules() {
        throw new IllegalStateException("Modules class");
    }

    public static class Core extends Module{
    }

    public static class Filters extends Module{
    }

    public static class Security extends Module{
    }
}
