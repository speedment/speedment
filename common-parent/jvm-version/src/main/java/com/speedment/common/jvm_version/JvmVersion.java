/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.common.jvm_version;

import com.speedment.common.jvm_version.internal.InternalJvmVersion;

/**
 *
 * @author Per Minborg
 */
public final class JvmVersion {

    private static final InternalJvmVersion INTERNAL = new InternalJvmVersion();

    private JvmVersion() {
        throw new UnsupportedOperationException();
    }

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
