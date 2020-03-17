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
package com.speedment.common.jvm_version;

import com.speedment.common.jvm_version.internal.InternalJvmVersion;

/**
 *
 * @author Per Minborg
 */
public final class JvmVersion {

    private static final InternalJvmVersion INTERNAL = new InternalJvmVersion();

    private JvmVersion() {}

    public static String getSpecificationTitle() {
        return INTERNAL.getSpecificationTitle();

    }

    public static String getSpecificationVersion() {
        return INTERNAL.getSpecificationVersion();
    }

    public static String getSpecificationVendor() {
        return INTERNAL.getSpecificationVendor();
    }

    public static String getImplementationTitle() {
        return INTERNAL.getImplementationTitle();

    }

    public static String getImplementationVersion() {
        return INTERNAL.getImplementationVersion();

    }

    public static String getImplementationVendor() {
        return INTERNAL.getImplementationVendor();

    }

    /**
     * Returns the <a href="#major">major</a> version number.
     *
     * @return The major version number
     */
    public static int major() {
        return INTERNAL.major();
    }

    /**
     * Returns the <a href="#minor">minor</a> version number or zero if it was
     * not set.
     *
     * @return The minor version number or zero if it was not set
     */
    public static int minor() {
        return INTERNAL.minor();
    }

    /**
     * Returns the <a href="#security">security</a> version number or zero if it
     * was not set.
     *
     * @return The security version number or zero if it was not set
     */
    public static int security() {
        return INTERNAL.security();
    }

    /**
     * Returns if this JVM runs Java 8.
     *
     * @return if this JVM runs Java 8
     */
    public static boolean isJava8() {
        return major() == 8;
    }

    /**
     * Returns if this JVM runs Java 9 or higher.
     *
     * @return if this JVM runs Java 9 or higher
     */
    public static boolean isJava9OrHigher() {
        return major() > 8;
    }

}
