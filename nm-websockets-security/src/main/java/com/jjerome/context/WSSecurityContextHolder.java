package com.jjerome.context;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

public class WSSecurityContextHolder {

    public static final String MODE_SESSION_LOCAL = "MODE_SESSION_LOCAL";

    public static final String MODE_GLOBAL = "MODE_GLOBAL";

    private static final String MODE_PRE_INITIALIZED = "MODE_PRE_INITIALIZED";

    private static String strategyName = System.getProperty("nm-websocket.security.strategy");

    private static WSSecurityContextHolderStrategy strategy;

    private static void initialize() {
        initializeStrategy();
    }

    private static void initializeStrategy() {
        if (MODE_PRE_INITIALIZED.equals(strategyName)) {
            Assert.state(strategy != null, "When using MODE_PRE_INITIALIZED, setContextHolderStrategy must be called with the fully constructed strategy");
        } else {
            if (!StringUtils.hasText(strategyName)) {
                strategyName = MODE_SESSION_LOCAL;
            }

            if (strategyName.equals(MODE_SESSION_LOCAL)) {
                strategy = new SessionLocalWSSecurityContextHolderStrategy();
            } else if (strategyName.equals(MODE_GLOBAL)) {
                strategy = new GlobalWSSecurityContextHolderStrategy();
            } else {
                try {
                    Class<?> clazz = Class.forName(strategyName);
                    Constructor<?> customStrategy = clazz.getConstructor();
                    strategy = (WSSecurityContextHolderStrategy)customStrategy.newInstance();
                } catch (Exception e) {
                    ReflectionUtils.handleReflectionException(e);
                }
            }
        }
    }

    public static void clearContext() {
        strategy.clearContext();
    }

    public static SecurityContext getContext() {
        return strategy.getContext();
    }

    public static Supplier<SecurityContext> getDeferredContext() {
        return strategy.getDeferredContext();
    }

    public static void setContext(SecurityContext context) {
        strategy.setContext(context);
    }

    public static void setDeferredContext(Supplier<SecurityContext> deferredContext) {
        strategy.setDeferredContext(deferredContext);
    }

    public static void setStrategyName(String strategyName) {
        WSSecurityContextHolder.strategyName = strategyName;
        initialize();
    }

    public static void setContextHolderStrategy(WSSecurityContextHolderStrategy strategy) {
        Assert.notNull(strategy, "WSSecurityContextHolderStrategy cannot be null");
        strategyName = MODE_PRE_INITIALIZED;
        WSSecurityContextHolder.strategy = strategy;
        initialize();
    }

    public static WSSecurityContextHolderStrategy getContextHolderStrategy() {
        return strategy;
    }

    public static SecurityContext emptyContext() {
        return strategy.emptyContext();
    }

    static {
        initialize();
    }
}
