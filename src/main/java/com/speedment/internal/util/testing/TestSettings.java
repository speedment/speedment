package com.speedment.internal.util.testing;

/**
 *
 * @author Per Minborg
 */
public class TestSettings {

    private static boolean testMode;

    public static void setTestMode(boolean testMode) {
        TestSettings.testMode = testMode;
    }

    public static boolean isTestMode() {
        return testMode;
    }

}
