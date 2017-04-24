/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.core.internal.util.java9;

import com.speedment.runtime.core.internal.util.testing.JavaVersionUtil;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class Java9StreamUtilTest {

    private static final Supplier<Stream<String>> STREAM_SUPPLIER = () -> Stream.of("a", "b", "c", "d", "e", "a");
    private static final Predicate<String> LESS_THAN_C = s -> "c".compareTo(s) > 0;

    @Test
    public void testPredicate() {
        STREAM_SUPPLIER.get().filter(LESS_THAN_C).forEach(System.out::println);
    }

    @Test
    public void testTakeWhile() {
        if (JavaVersionUtil.is8()) {
            try {
                Java9StreamUtil.takeWhile(STREAM_SUPPLIER.get(), LESS_THAN_C);
                fail("takeWhile should not be supported by this Java version");
            } catch (UnsupportedOperationException e) {
                // Expected for Java 8
            }

        } else {
            final List<String> expected = Arrays.asList("a", "b");
            final List<String> actual
                = Java9StreamUtil.takeWhile(STREAM_SUPPLIER.get(), LESS_THAN_C).collect(toList());

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testDropWhile() {
        if (JavaVersionUtil.is8()) {
            try {
                Java9StreamUtil.dropWhile(STREAM_SUPPLIER.get(), LESS_THAN_C);
                fail("dropWhile should not be supported by this Java version");
            } catch (UnsupportedOperationException e) {
                // Expected for Java 8
            }

        } else {
            final List<String> expected = Arrays.asList("c", "d", "e", "a");
            final List<String> actual
                = Java9StreamUtil.dropWhile(STREAM_SUPPLIER.get(), LESS_THAN_C).collect(toList());

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testFilter() {
        
        final List<String> expected = STREAM_SUPPLIER.get()
            .filter(LESS_THAN_C)
            .collect(toList());

        final List<String> actual = Java9StreamUtil.filter(STREAM_SUPPLIER.get(), LESS_THAN_C)
            .collect(toList());

        assertEquals(expected, actual);

    }

}
