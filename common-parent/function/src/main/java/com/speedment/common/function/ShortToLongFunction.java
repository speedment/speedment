package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code short} returns
 * a {@code long}.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface ShortToLongFunction {

    /**
     * Applies this function on the specified {@code short}, returning a
     * {@code long}.
     *
     * @param value  the input value
     * @return       the output
     */
    long applyAsLong(short value);
}
