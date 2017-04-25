package com.speedment.runtime.core.internal.util.testing;

import static com.speedment.runtime.core.internal.util.testing.JavaVersionUtil.JavaVersion.*;

/**
 *
 * @author Per Minborg
 */
public final class JavaVersionUtil {

    public enum JavaVersion {
        UNKNOWN, EIGHT, NINE, TEN;
    }

    public static boolean is8() {
        return getJavaVersion() == EIGHT;
    }

    public static boolean is9() {
        return getJavaVersion() == NINE;
    }

    public static boolean is10() {
        return getJavaVersion() == TEN;
    }

    public static JavaVersion getJavaVersion() {
        final String version = System.getProperty("java.specification.version");
        if (version == null) {
            return UNKNOWN;
        }
        if (version.startsWith("1.8")) {
            return EIGHT;
        }
        if (version.startsWith("9")) {
            return NINE;
        }
        if (version.startsWith("10")) {
            return TEN;
        }
        return UNKNOWN;
    }

}
