package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code float} returns
 * a {@code double}.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface FloatToDoubleFunction {

    /**
     * Applies this function on the specified {@code float}, returning a
     * {@code double}.
     *
     * @param value  the input value
     * @return       the output
     */
    double applyAsDouble(float value);
}
