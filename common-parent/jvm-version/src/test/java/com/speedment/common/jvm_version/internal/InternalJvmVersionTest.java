/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class InternalJvmVersionTest {

    private static final InternalJvmVersion instance = new InternalJvmVersion();

    @Test
    public void testGetSpecificationTitle() {
        final String result = instance.getSpecificationTitle();
        assertNotNull(result);
    }

    @Test
    public void testGetSpecificationVersion() {
        final String result = instance.getSpecificationVersion();
        assertNotNull(result);
    }

    @Test
    public void testGetSpecificationVendor() {
        final String result = instance.getSpecificationVendor();
        assertNotNull(result);
    }

    @Test
    public void testGetImplementationTitle() {
        final String result = instance.getImplementationTitle();
        assertNotNull(result);
    }

    @Test
    public void testGetImplementationVersion() {
        final String result = instance.getImplementationVersion();
        assertNotNull(result);
    }

    @Test
    public void testGetImplementationVendor() {
        final String result = instance.getImplementationVendor();
        assertNotNull(result);
    }

    @Test
    public void testMajor() {
        final int result = instance.major();
        assertTrue(result >= 8);
    }

    @Test
    public void testMinor() {
        final int result = instance.minor();
        assertTrue(result >= 0);
    }

    @Test
    public void testSecurity() {
        final int result = instance.security();
        assertTrue(result >= 0);
    }

}
