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
package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.model.Project;
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
public class ProjectImplTest {

    ProjectImpl instance;

    public ProjectImplTest() {
        instance = new ProjectImpl();
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
     * Test of setDefaults method, of class ProjectImpl.
     */
    @Test
    public void testSetDefaults() {
        System.out.println("setDefaults");
        instance.setDefaults();
        assertNotNull(instance.getName());
        assertNotNull(instance.getPacketName());
        assertNotNull(instance.getPacketLocation());
    }

    /**
     * Test of getPacketName method, of class ProjectImpl.
     */
    @Test
    public void testGetPacketName() {
        System.out.println("getPacketName");
        assertNotNull(instance.getPacketName());
    }

    /**
     * Test of setPacketName method, of class ProjectImpl.
     */
    @Test
    public void testSetPacketName() {
        System.out.println("setPacketName");
        final String packetName = "packet.name";
        instance.setPacketName(packetName);
        assertEquals(packetName, instance.getPacketName());
    }

    /**
     * Test of getPacketLocation method, of class ProjectImpl.
     */
    @Test
    public void testGetPacketLocation() {
        System.out.println("getPacketLocation");
        assertNotNull(instance.getPacketLocation());
    }

    /**
     * Test of setPacketLocation method, of class ProjectImpl.
     */
    @Test
    public void testSetPacketLocation() {
        System.out.println("setPacketLocation");
        final String packetLocation = "packet/location";
        instance.setPacketLocation(packetLocation);
        assertEquals(packetLocation, instance.getPacketLocation());
    }

}
