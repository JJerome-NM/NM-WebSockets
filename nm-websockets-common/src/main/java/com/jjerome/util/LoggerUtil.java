package com.jjerome.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    public static void disableReflectionsInfoLogs(){
        ((Logger)LoggerFactory.getLogger("org.reflections")).setLevel(Level.WARN);
    }

    public static void enableReflectionsLogs(){
        ((Logger)LoggerFactory.getLogger("org.reflections")).setLevel(Level.INFO);
    }
}
