/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.core.internal.util.testing;

import static com.speedment.runtime.core.internal.util.testing.JavaVersionUtil.JavaVersion.*;

/**
 *
 * @author Per Minborg
 */
public final class JavaVersionUtil {

    public enum JavaVersion {
        UNKNOWN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN, FOURTEEN
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

    public static boolean is11() {
        return getJavaVersion() == ELEVEN;
    }

    public static boolean is12() {
        return getJavaVersion() == TWELVE;
    }

    public static boolean is13() {
        return getJavaVersion() == THIRTEEN;
    }

    public static boolean is14() {
        return getJavaVersion() == FOURTEEN;
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
        if (version.startsWith("11")) {
            return ELEVEN;
        }
        if (version.startsWith("12")) {
            return TWELVE;
        }
        if (version.startsWith("13")) {
            return THIRTEEN;
        }
        if (version.startsWith("14")) {
            return FOURTEEN;
        }
        return UNKNOWN;
    }

}
