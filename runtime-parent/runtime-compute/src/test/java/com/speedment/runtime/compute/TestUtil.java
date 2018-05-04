package com.speedment.runtime.compute;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
final class TestUtil {

    static final double EPSILON = 10e-10;
    static final String STRING = "Tryggve";

    private TestUtil() {
    }

    public static Stream<String> strings() {
        return Stream.of(
            "Tryggve",
            "Arne",
            "Sven",
            "Glenn",
            "Pippilotta Viktualia Rullgardina Krusmynta Efraimsdotter Långstrump"
        );

    }

}
