package com.speedment.runtime.field;

import java.util.List;
import java.util.function.Function;
import static java.util.stream.Collectors.joining;
import org.junit.Assert;

/**
 *
 * @author Emil Forslund
 * @since  3.0.3
 */
public final class TestUtil {
    
    public static <E> void assertListEqual(String msg, List<E> actual, List<E> expected, Function<E, String> toString) {
        Assert.assertTrue(msg +
            "\n  'actual'   = " + listToString(actual, toString) +
            "\n  'expected' = " + listToString(expected, toString), 
            actual.equals(expected)
        );
    }
    
    private static <E> String listToString(List<E> list, Function<E, String> toString) {
        if (list == null) {
            return "null";
        } else {
            return list.stream()
                .map(toString)
                .collect(joining(",", "[", "]"));
        }
    }
    
    private TestUtil() {}
}