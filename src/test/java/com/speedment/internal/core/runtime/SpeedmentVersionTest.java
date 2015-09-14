/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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

package com.speedment.internal.core.runtime;

import com.speedment.SpeedmentVersion;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pemi
 */
public class SpeedmentVersionTest {
    
    private static final String EXPECTED_IMPLEMENTATION_VERSION = "2.1.1";
    private static final String EXPECTED_SPECIFICATION_VERSION = "2.1";

    public SpeedmentVersionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private Package getPackage() {

        //System.out.println(SpeedmentApplicationLifecycle.class.getPackage().getImplementationVersion());
        return SpeedmentApplicationLifecycle.class.getPackage();
    }

    private String getFromManifest(String tag) {
        try (final InputStream pomPropertiesStream = SpeedmentVersion.class
                .getResourceAsStream("/META-INF/MANIFEST.MF")) {
            final Properties pomProperties = new Properties();
            pomProperties.load(pomPropertiesStream);
            return pomProperties.getProperty("Implementation-Version");
        } catch (IOException ioe) {

        }
        throw new IllegalArgumentException(tag + " not found");
    }

    @Test
    public void testGetImplementationTitle() {
        System.out.println("getImplementationTitle");
        //String expResult = getPackage().getImplementationTitle();
        String expResult = "Speedment";
        String result = SpeedmentVersion.getImplementationTitle();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetImplementationVendor() {
        System.out.println("getImplementationVendor");
        //String expResult = getPackage().getImplementationVendor();
        String expResult = "Speedment Inc.";
        String result = SpeedmentVersion.getImplementationVendor();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetImplementationVersion() {
        System.out.println("getImplementationVersion");
//        String expResult = getPackage().getImplementationVersion();
//        String expResult2 = getFromManifest("ImplementationVersion");

        // Todo: Implement a real test. Like extract version from POM or MANIFEST.MF
        String result = SpeedmentVersion.getImplementationVersion();
        assertEquals(EXPECTED_IMPLEMENTATION_VERSION, result);
    }
    
    @Test
    public void testGetSpecificationVersion() {
        System.out.println("getSpecificationVersion");
        String expResult = EXPECTED_SPECIFICATION_VERSION;
        String result = SpeedmentVersion.getSpecificationVersion();
        assertEquals(expResult, result);
    }
    
//
//    @Test
//    public void testGetSpecificationTitle() {
//        System.out.println("getSpecificationTitle");
//        String expResult = getPackage().getSpecificationVendor();
//        System.out.println(expResult);
//        String result = SpeedmentVersion.getSpecificationVendor();
//        assertEquals(expResult, result);
//    }
//
//    @Test
//    public void testGetSpecificationVendor() {
//        System.out.println("getSpecificationVendor");
//        String expResult = getPackage().getSpecificationVendor();
//        System.out.println(expResult);
//        String result = SpeedmentVersion.getSpecificationVendor();
//        assertEquals(expResult, result);
//    }
//

    

}
