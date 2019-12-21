package com.speedment.common.injector.internal.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

final class SetUtilTest {

    @Test
    void unmodifiableSet() {
        final String[] words = {"A", "B", "C"};
        final Set<String> set = SetUtil.unmodifiableSet(words);
        assertEquals(Stream.of(words).collect(toSet()), set);
        assertThrows(UnsupportedOperationException.class, set::clear);
    }
}