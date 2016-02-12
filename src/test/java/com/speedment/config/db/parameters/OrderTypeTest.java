/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.config.db.parameters;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Per Minborg
 */
public class OrderTypeTest {

    final AtomicBoolean asc = new AtomicBoolean();
    final AtomicBoolean desc = new AtomicBoolean();
    final AtomicBoolean none = new AtomicBoolean();
    final Supplier<String> ascSupplier = () -> {
        asc.set(true);
        return "A";
    };
    final Supplier<String> descSupplier = () -> {
        desc.set(true);
        return "B";
    };
    final Supplier<String> noneSupplier = () -> {
        none.set(true);
        return "C";
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
        none.set(false);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testValues() {
        System.out.println("values");
        assertEquals(3, OrderType.values().length);
    }

    @Test
    public void testSelectLazilyASC() {
        System.out.println("selectLazily");
        assertEquals("A", OrderType.ASC.selectLazily(ascSupplier, descSupplier, noneSupplier));
        assertAscSelected();
    }

    @Test
    public void testSelectLazilyDESC() {
        System.out.println("selectLazily");
        assertEquals("B", OrderType.DESC.selectLazily(ascSupplier, descSupplier, noneSupplier));
        assertDescSelected();
    }

    @Test
    public void testSelectLazilyNONE() {
        System.out.println("selectLazily");
        assertEquals("C", OrderType.NONE.selectLazily(ascSupplier, descSupplier, noneSupplier));
        assertNoneSelected();
    }

    @Test
    public void testSelect() {
        System.out.println("select");
        assertEquals("A", OrderType.ASC.select("A", "B", "C"));
        assertEquals("B", OrderType.DESC.select("A", "B", "C"));
        assertEquals("C", OrderType.NONE.select("A", "B", "C"));
    }

    @Test
    public void testSelectRunnableASC() {
        System.out.println("selectRunnable");
        OrderType.ASC.selectRunnable(() -> asc.set(true), () -> desc.set(true), () -> none.set(true));
        assertAscSelected();
    }

    @Test
    public void testSelectRunnableDesc() {
        System.out.println("selectRunnable");
        OrderType.DESC.selectRunnable(() -> asc.set(true), () -> desc.set(true), () -> none.set(true));
        assertDescSelected();
    }

    @Test
    public void testSelectRunnableNone() {
        System.out.println("selectRunnable");
        OrderType.NONE.selectRunnable(() -> asc.set(true), () -> desc.set(true), () -> none.set(true));
        assertNoneSelected();
    }

    private void assertAscSelected() {
        assertTrue(asc.get());
        assertFalse(desc.get());
        assertFalse(none.get());
    }

    private void assertDescSelected() {
        assertFalse(asc.get());
        assertTrue(desc.get());
        assertFalse(none.get());
    }

    private void assertNoneSelected() {
        assertFalse(asc.get());
        assertFalse(desc.get());
        assertTrue(none.get());
    }

}
