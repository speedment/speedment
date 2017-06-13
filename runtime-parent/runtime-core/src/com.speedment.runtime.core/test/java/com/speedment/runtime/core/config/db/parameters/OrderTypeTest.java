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
package com.speedment.runtime.core.config.db.parameters;

import com.speedment.runtime.config.parameter.OrderType;
import org.junit.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static org.junit.Assert.*;

/**
 *
 * @author Per Minborg
 */
public class OrderTypeTest {

    final AtomicBoolean asc = new AtomicBoolean();
    final AtomicBoolean desc = new AtomicBoolean();
    final Supplier<String> ascSupplier = () -> {
        asc.set(true);
        return "A";
    };
    final Supplier<String> descSupplier = () -> {
        desc.set(true);
        return "B";
    };


    public OrderTypeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        asc.set(false);
        desc.set(false);
       // none.set(false);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testValues() {
        assertEquals(3, OrderType.values().length);
    }

    @Test
    public void testSelectLazilyASC() {
        assertEquals("A", OrderType.ASC.selectLazily(ascSupplier, descSupplier/*, noneSupplier*/));
        assertAscSelected();
    }

    @Test
    public void testSelectLazilyDESC() {
        assertEquals("B", OrderType.DESC.selectLazily(ascSupplier, descSupplier/*, noneSupplier*/));
        assertDescSelected();
    }

//    @Test
//    public void testSelectLazilyNONE() {
//        System.out.println("selectLazily");
//        assertEquals("C", OrderType.NONE.selectLazily(ascSupplier, descSupplier/*, noneSupplier*/));
//        assertNoneSelected();
//    }

    @Test
    public void testSelect() {
        assertEquals("A", OrderType.ASC.select("A", "B"));
        assertEquals("B", OrderType.DESC.select("A", "B"));
        assertEquals("A", OrderType.NONE.select("A", "B"));
    }

    @Test
    public void testSelectRunnableASC() {
        OrderType.ASC.selectRunnable(() -> asc.set(true), () -> desc.set(true));
        assertAscSelected();
    }

    @Test
    public void testSelectRunnableDesc() {
        OrderType.DESC.selectRunnable(() -> asc.set(true), () -> desc.set(true));
        assertDescSelected();
    }

//    @Test
//    public void testSelectRunnableNone() {
//        System.out.println("selectRunnable");
//        OrderType.NONE.selectRunnable(() -> asc.set(true), () -> desc.set(true));
//        assertNoneSelected();
//    }

    private void assertAscSelected() {
        assertTrue(asc.get());
        assertFalse(desc.get());
        //assertFalse(none.get());
    }

    private void assertDescSelected() {
        assertFalse(asc.get());
        assertTrue(desc.get());
        //assertFalse(none.get());
    }

    private void assertNoneSelected() {
        assertFalse(asc.get());
        assertFalse(desc.get());
       // assertTrue(none.get());
    }

}
