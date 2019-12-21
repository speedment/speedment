package com.speedment.common.injector.internal.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class StringUtilTest {

    @Test
    void commaAnd0() {
        assertEquals("", StringUtil.commaAnd());
    }

    @Test
    void commaAnd1() {
        assertEquals("A", StringUtil.commaAnd("A"));
    }

    @Test
    void commaAnd2() {
        assertEquals("A and B", StringUtil.commaAnd("A", "B"));
    }

    @Test
    void commaAnd3() {
        assertEquals("A, B and C", StringUtil.commaAnd("A", "B", "C"));
    }
}