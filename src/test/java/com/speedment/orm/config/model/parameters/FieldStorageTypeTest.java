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
package com.speedment.orm.config.model.parameters;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.config.model.impl.ColumnImpl;
import com.speedment.orm.config.model.impl.SchemaImpl;
import com.speedment.orm.config.model.impl.TableImpl;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
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
public class FieldStorageTypeTest {

    private static final Column column = new ColumnImpl();
    private static final Table table = new TableImpl().add(column);
    private static final Schema schema = new SchemaImpl().add(table);

    public FieldStorageTypeTest() {
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
     * Test of getName method, of class FieldStorageType.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        assertEquals("Wrapper class", FieldStorageType.WRAPPER.getName());
    }

    /**
     * Test of findByNameIgnoreCase method, of class FieldStorageType.
     */
    @Test
    public void testFindByNameIgnoreCase() {
        System.out.println("findByNameIgnoreCase");
        Stream.of(FieldStorageType.values()).forEach(f -> {
            assertEquals(f, FieldStorageType.findByNameIgnoreCase(f.getName()).get());
            assertEquals(f, FieldStorageType.findByNameIgnoreCase(f.getName().toUpperCase()).get());
            assertEquals(f, FieldStorageType.findByNameIgnoreCase(f.getName().toLowerCase()).get());
        });
    }

    /**
     * Test of streamFor method, of class FieldStorageType.
     */
    @Test
    public void testStreamFor() {
        System.out.println("streamFor");
        Set<FieldStorageType> result = FieldStorageType.streamFor(column).collect(toSet());
        Set<FieldStorageType> expected = Stream.of(FieldStorageType.values()).collect(toSet());
        assertEquals(expected, result);
    }

    /**
     * Test of defaultFor method, of class FieldStorageType.
     */
    @Test
    public void testDefaultFor() {
        System.out.println("defaultFor");
        assertEquals(FieldStorageType.INHERIT, FieldStorageType.defaultFor(column));
        assertEquals(FieldStorageType.INHERIT, FieldStorageType.defaultFor(table));
        assertEquals(FieldStorageType.WRAPPER, FieldStorageType.defaultFor(schema));
    }

}
