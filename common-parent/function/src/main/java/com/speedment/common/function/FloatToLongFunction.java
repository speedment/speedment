package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code float} returns
 * a {@code long}.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface FloatToLongFunction {

    /**
     * Applies this function on the specified {@code float}, returning a
     * {@code long}.
     *
     * @param value  the input value
     * @return       the output
     */
    long applyAsLong(float value);
}
