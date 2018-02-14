package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code byte} returns
 * a {@code double}.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface ByteToDoubleFunction {

    /**
     * Applies this function on the specified {@code byte}, returning a
     * {@code double}.
     *
     * @param value  the input value
     * @return       the output
     */
    double applyAsDouble(byte value);
}
