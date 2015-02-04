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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.codegen.model.package_;

import com.speedment.codegen.model.CodeModel;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author pemi
 */
public class Package_Test {

    public Package_Test() {
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

    /**
     * Test of getName_ method, of class Package_.
     */
    @Test
    @Ignore
    public void testGetName_() {
        System.out.println("getName_");
        Package_ instance = new Package_();
        String expResult = "";
        CharSequence result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName_ method, of class Package_.
     */
    @Test
    @Ignore
    public void testSetName_() {
        System.out.println("setName_");
        String name_ = "";
        Package_ instance = new Package_();
        instance.setName(name_);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getModelType method, of class Package_.
     */
    @Test
    @Ignore
    public void testGetModelType() {
        System.out.println("getModelType");
        Package_ instance = new Package_();
        CodeModel.Type expResult = null;
        CodeModel.Type result = instance.getModelType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPackage method, of class Package_.
     */
    @Test
    @Ignore
    public void testGetPackage() {
        System.out.println("getPackage");
        Package_ instance = new Package_();
        Package_ expResult = null;
        Package_ result = instance.getPackage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPackage method, of class Package_.
     */
    @Test
    @Ignore
    public void testSetPackage() {
        System.out.println("setPackage");
        Package_ package_ = null;
        Package_ instance = new Package_();
        Package_ expResult = null;
        Package_ result = instance.setPackage(package_);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of byQualifiedName method, of class Package_.
     */
    @Test
    public void testByQualifiedName() {
        System.out.println("byQualifiedName");
        final Set<CharSequence> testSequences
                = Stream.of("java.lang.String", "Foo", "foo.BaR", "some.very.long.path.that.is.un.realistic.Class", "a").collect(Collectors.toSet());
        testSequences.stream().forEach(s -> {
            final Package_ result = Package_.by(s);
            assertEquals(s, result.toString());
        });

    }

}
