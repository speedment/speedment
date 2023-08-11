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

    // This is unfortunately what we decided early on ...
    public enum JavaVersion {
        UNKNOWN(0), EIGHT(8), NINE(9), TEN(10), ELEVEN(11), TWELVE(12), THIRTEEN(13), FOURTEEN(14), FIFTEEN(15),
        SIXTEEN(16), SEVENTEEN(17), EIGHTEEN(18), NINETEEN(19),
        TWENTY(20), TWENTY_ONE(21), TWENTY_TWO(22), TWENTY_THREE(23), TWENTY_FOUR(24), TWENTY_FIVE(25), TWENTY_SIX(26),
        TWENTY_SEVEN(27), TWENTY_EIGHT(28), TWENTY_NINE(29), THIRTY(30);
        ;

        private final int version;

        JavaVersion(int version) {
            this.version = version;
        }
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

    public static boolean is15() {
        return getJavaVersion() == FIFTEEN;
    }

    public static boolean isLessThan(JavaVersion version) {
        return getJavaVersion().ordinal() < version.ordinal();
    }

    public static boolean isLessThanOrEqualTo(JavaVersion version) {
        return getJavaVersion().ordinal() <= version.ordinal();
    }

    public static boolean isGreaterThan(JavaVersion version) {
        return getJavaVersion().ordinal() > version.ordinal();
    }

    public static boolean isGreaterThanOrEqualTo(JavaVersion version) {
        return getJavaVersion().ordinal() >= version.ordinal();
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
        if (version.startsWith("15")) {
            return FIFTEEN;
        }
        if (version.startsWith("16")) {
            return SIXTEEN;
        }
        if (version.startsWith("17")) {
            return SEVENTEEN;
        }
        if (version.startsWith("18")) {
            return EIGHTEEN;
        }
        if (version.startsWith("19")) {
            return NINETEEN;
        }
        if (version.startsWith("20")) {
            return TWENTY;
        }
        if (version.startsWith("21")) {
            return TWENTY_ONE;
        }
        if (version.startsWith("22")) {
            return TWENTY_TWO;
        }
        if (version.startsWith("23")) {
            return TWENTY_THREE;
        }
        if (version.startsWith("24")) {
            return TWENTY_FOUR;
        }
        if (version.startsWith("25")) {
            return TWENTY_FIVE;
        }
        if (version.startsWith("26")) {
            return TWENTY_SIX;
        }
        if (version.startsWith("27")) {
            return TWENTY_SEVEN;
        }
        if (version.startsWith("28")) {
            return TWENTY_EIGHT;
        }
        if (version.startsWith("29")) {
            return TWENTY_NINE;
        }
        if (version.startsWith("30")) {
            return THIRTY;
        }
        return UNKNOWN;
    }

}
