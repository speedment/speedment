package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code byte} returns
 * a {@code long}.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface ByteToLongFunction {

    /**
     * Applies this function on the specified {@code byte}, returning a
     * {@code long}.
     *
     * @param value  the input value
     * @return       the output
     */
    long applyAsLong(byte value);
}
