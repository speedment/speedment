/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author pemi
 */
public class SpeedmentVersionTest {
    
    private static final String EXPECTED_IMPLEMENTATION_VERSION = "3.0.0-EA";
    private static final String EXPECTED_SPECIFICATION_VERSION = "3.0";

    @Test
    public void testGetImplementationTitle() {
        final String expResult = "Speedment";
        final String result = SpeedmentVersion.getImplementationTitle();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetImplementationVendor() {
        final String expResult = "Speedment Inc.";
        final String result = SpeedmentVersion.getImplementationVendor();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetImplementationVersion() {
        // Todo: Implement a real test. Like extract version from POM or MANIFEST.MF
        final String result = SpeedmentVersion.getImplementationVersion();
        assertEquals(EXPECTED_IMPLEMENTATION_VERSION, result);
    }
    
    @Test
    public void testGetSpecificationVersion() {
        final String expResult = EXPECTED_SPECIFICATION_VERSION;
        final String result = SpeedmentVersion.getSpecificationVersion();
        assertEquals(expResult, result);
    }
}