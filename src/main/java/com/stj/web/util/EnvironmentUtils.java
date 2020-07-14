package com.stj.web.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.apache.commons.lang3.StringUtils.defaultString;

public final class EnvironmentUtils {
    public static final String DEVELOPMENT = "DEV";

    public static final String TEST = "TEST";

    public static final String PRODUCTION = "PROD";

    public static final String LOCAL = "LOCAL";

    private static final String WLSINSTANCE = "weblogic.Name";

    private static final String ENV_KEY = "stjames.implementation.environment";

    private EnvironmentUtils() {
        // private constructor for utility class
    }

    public static String getEnvironment() {
        String env = System.getProperty(ENV_KEY);
        if (DEVELOPMENT.equalsIgnoreCase(env) || TEST.equalsIgnoreCase(env) || PRODUCTION.equalsIgnoreCase(env)) {
            return env.toUpperCase();
        }
        return LOCAL;
    }

    public static boolean isLocal() {
        return getEnvironment().equals(LOCAL);
    }

    public static boolean isDevelopment() {
        return getEnvironment().equals(DEVELOPMENT);
    }

    public static boolean isTest() {
        return getEnvironment().equals(TEST);
    }

    public static boolean isProduction() {
        return getEnvironment().equals(PRODUCTION);
    }

    public static String getWlsInstanceAndHost() {

        String instance = System.getProperty(WLSINSTANCE);
        String host = "";

        try {

            host = InetAddress.getLocalHost().getHostName();

        } catch (UnknownHostException eUHE) {
        }

        return defaultString(instance) + "@" + defaultString(host);

    }
}
