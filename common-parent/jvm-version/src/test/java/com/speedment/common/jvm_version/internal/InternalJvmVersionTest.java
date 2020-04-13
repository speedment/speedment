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


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Per Minborg
 */
@Execution(ExecutionMode.CONCURRENT)
final class InternalJvmVersionTest {

    private static final InternalJvmVersion instance = new InternalJvmVersion();

    @Test
    void testGetSpecificationTitle() {
        final String result = instance.getSpecificationTitle();
        assertNotNull(result);
    }

    @Test
    void testGetSpecificationVersion() {
        final String result = instance.getSpecificationVersion();
        assertNotNull(result);
    }

    @Test
    void testGetSpecificationVendor() {
        final String result = instance.getSpecificationVendor();
        assertNotNull(result);
    }

    @Test
    void testGetImplementationTitle() {
        final String result = instance.getImplementationTitle();
        assertNotNull(result);
    }

    @Test
    void testGetImplementationVersion() {
        final String result = instance.getImplementationVersion();
        assertNotNull(result);
    }

    @Test
    void testGetImplementationVendor() {
        final String result = instance.getImplementationVendor();
        assertNotNull(result);
    }

    @Test
    void testMajor() {
        final int result = instance.major();
        assertTrue(result >= 8);
    }

    @Test
    void testMinor() {
        final int result = instance.minor();
        assertTrue(result >= 0);
    }

    @Test
    void testSecurity() {
        final int result = instance.security();
        assertTrue(result >= 0);
    }

}
