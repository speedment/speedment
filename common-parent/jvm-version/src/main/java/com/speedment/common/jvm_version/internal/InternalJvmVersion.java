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
package com.speedment.common.jvm_version.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Per Minborg
 */
public final class InternalJvmVersion {

    private final String specificationTitle;
    private final String specificationVersion;
    private final String specificationVendor;

    private final String implementationTitle;
    private final String implementationVersion;
    private final String implementationVendor;

    private final int major;
    private final int minor;
    private final int security;

    public InternalJvmVersion() {
        specificationTitle = System.getProperty("java.vm.specification.name");
        specificationVersion = System.getProperty("java.vm.specification.version");
        specificationVendor = System.getProperty("java.vm.specification.vendor");

        implementationTitle = System.getProperty("java.specification.name");
        major = version("major", 1);
        minor = version("minor", 2);
        security = version("security", 3);
        implementationVersion = String.format("%d.%d.%d", major, minor, security);
        implementationVendor = System.getProperty("java.specification.vendor");
    }

    public String getSpecificationTitle() {
        return specificationTitle;
    }

    public String getSpecificationVersion() {
        return specificationVersion;
    }

    public String getSpecificationVendor() {
        return specificationVendor;
    }

    public String getImplementationTitle() {
        return implementationTitle;
    }

    public String getImplementationVersion() {
        return implementationVersion;
    }

    public String getImplementationVendor() {
        return implementationVendor;
    }

    /**
     * Returns the <a href="#major">major</a> version number.
     *
     * @return The major version number
     */
    public int major() {
        return major;
    }

    /**
     * Returns the <a href="#minor">minor</a> version number or zero if it was
     * not set.
     *
     * @return The minor version number or zero if it was not set
     */
    public int minor() {
        return minor;
    }

    /**
     * Returns the <a href="#security">security</a> version number or zero if it
     * was not set.
     *
     * @return The security version number or zero if it was not set
     */
    public int security() {
        return security;
    }

    private int version(String java9Name, int java8Index) {
        try {
            // Try Java 9 first
            final Method method = Runtime.class.getDeclaredMethod("version");
            if (method != null) {
                final Object version = method.invoke(Runtime.getRuntime());
                final Class<?> clazz = Class.forName("java.lang.Runtime$Version");
                return (Integer) clazz.getDeclaredMethod(java9Name).invoke(version);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException unused) {
            // we are pre Java 9
        }
        try {
            return Integer.parseInt(System.getProperty("java.runtime.version").split("([\\._-])")[java8Index]);
        } catch (Exception t) {
            return 0;
        }
    }

}