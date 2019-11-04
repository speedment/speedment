package com.speedment.common.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class LongCacheTest {

    @Test
    void getOrCompute() {
        final LongCache<String> lc = new LongCache<>(10);
        final long actual = lc.getOrCompute("one", () -> 1L);
        assertEquals(1L, actual);
    }

    @Test
    void testToString() {
        final LongCache<String> lc = new LongCache<>(10);
        final long actual = lc.getOrCompute("Olle", () -> 42L);
        assertTrue(lc.toString().contains("Olle"));
        assertTrue(lc.toString().contains("42"));
    }
}