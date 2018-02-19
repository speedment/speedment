package com.speedment.common.function;

/**
 * Functional interface representing side-effect free methods with the signature
 * {@code boolean test(byte, byte)}.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface BiBytePredicate {

    /**
     * Tests this predicate with the specified arguments. Implementations of
     * this interface should be without side-effects.
     *
     * @param first   the first argument
     * @param second  the second argument
     * @return        {@code true} if the test passed, else {@code false}
     */
    boolean test(byte first, byte second);

}
