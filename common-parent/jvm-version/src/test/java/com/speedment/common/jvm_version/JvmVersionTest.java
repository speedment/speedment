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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Just test that things are returned. The actual
 * values returned are tested elsewhere.
 *
 */
final class JvmVersionTest {

    @Test
    void getSpecificationTitle() {
        assertNotNull(JvmVersion.getSpecificationTitle());
    }

    @Test
    void getSpecificationVersion() {
        assertNotNull(JvmVersion.getSpecificationVersion());
    }

    @Test
    void getSpecificationVendor() {
        assertNotNull(JvmVersion.getSpecificationVendor());
    }

    @Test
    void getImplementationTitle() {
        assertNotNull(JvmVersion.getImplementationTitle());
    }

    @Test
    void getImplementationVersion() {
        assertNotNull(JvmVersion.getImplementationVersion());
    }

    @Test
    void getImplementationVendor() {
        assertNotNull(JvmVersion.getImplementationVendor());
    }

    @Test
    void major() {
        assertFalse(JvmVersion.major() == 0);
    }

    @Test
    void minor() {
        assertTrue(JvmVersion.minor() >= 0);
    }

    @Test
    void security() {
        assertTrue(JvmVersion.security() >= 0);
    }

    @Test
    void isJava8() {
        // We cannot build under Java 8 anyhow...
        assertFalse(JvmVersion.isJava8());
    }

    @Test
    void isJava9OrHigher() {
        assertTrue(JvmVersion.isJava9OrHigher());
    }
}