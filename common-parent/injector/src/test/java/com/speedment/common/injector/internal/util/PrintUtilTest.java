package com.speedment.common.injector.internal.util;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class PrintUtilTest {

    private static final String CHARS = IntStream.rangeClosed('a', 'z').mapToObj(i -> (char) i).map(Object::toString).collect(joining());

    @Test
    void limitSmall() {
        assertEquals("a", PrintUtil.limit(CHARS, 1));
    }

    @Test
    void limitLarge() {
        assertEquals(CHARS, PrintUtil.limit(CHARS, CHARS.length()));
    }

    @Test
    void limit() {
        assertEquals("abc...wxyz", PrintUtil.limit(CHARS, 10));
        assertEquals("abcd...wxyz", PrintUtil.limit(CHARS, 11));
        assertEquals("abcd...vwxyz", PrintUtil.limit(CHARS, 12));
    }

}