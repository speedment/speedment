package com.speedment.common.injector.internal;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

final class InjectableTest {

    private static final int VALUE = 1;
    private static final Supplier<Integer> SUPPLIER = () -> VALUE;


    @Test
    void get() {
        final Injectable<Integer> injectable = new Injectable<>(Integer.class, SUPPLIER);
        assertEquals(Integer.class, injectable.get());
    }

    @Test
    void hasSupplierYes() {
        final Injectable<Integer> injectable = new Injectable<>(Integer.class, SUPPLIER);
        assertTrue(injectable.hasSupplier());
    }

    @Test
    void hasSupplierNo() {
        final Injectable<Integer> injectable = new Injectable<>(Integer.class, null);
        assertFalse(injectable.hasSupplier());
    }

    @Test
    void supplierYes() {
        final Injectable<Integer> injectable = new Injectable<>(Integer.class, SUPPLIER);
        assertEquals(VALUE, injectable.supplier().get());
    }

    @Test
    void supplierNo() {
        final Injectable<Integer> injectable = new Injectable<>(Integer.class, null);
        assertThrows(UnsupportedOperationException.class, injectable::supplier);
    }

    @Test
    void testEquals() {
        final Injectable<Integer> injectable1 = new Injectable<>(Integer.class, SUPPLIER);
        final Injectable<Integer> injectable2 = new Injectable<>(Integer.class, SUPPLIER);
        final Injectable<Integer> injectable3 = new Injectable<>(Integer.class, () -> 42);
        final Injectable<String> injectable4 = new Injectable<>(String.class, null);

        assertEquals(injectable1, injectable1);
        assertEquals(injectable2, injectable2);
        assertEquals(injectable1, injectable2);
        assertEquals(injectable2, injectable1);

        assertNotEquals(injectable1, injectable3);
        assertNotEquals(injectable3, injectable4);
        assertNotEquals(injectable1, injectable4);
        assertFalse(injectable1.equals(1));
        assertFalse(injectable1.equals(null));
    }

    @Test
    void testHashCode() {
        final int injectable1 = new Injectable<>(Integer.class, SUPPLIER).hashCode();
        final int injectable2 = new Injectable<>(Integer.class, SUPPLIER).hashCode();

        assertEquals(injectable1, injectable1);
        assertEquals(injectable2, injectable2);
        assertEquals(injectable1, injectable2);
        assertEquals(injectable2, injectable1);
    }
}