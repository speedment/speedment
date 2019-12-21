package com.speedment.common.injector.internal.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class StringUtilTest {

    @Test
    void commaAnd() {
        assertEquals("A, B and C", StringUtil.commaAnd("A", "B", "C"));
    }
}