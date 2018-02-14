package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code boolean} returns
 * a {@code double}.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface BooleanToDoubleFunction {

    /**
     * Applies this function on the specified {@code boolean}, returning a
     * {@code double}.
     *
     * @param value  the input value
     * @return       the output
     */
    double applyAsDouble(boolean value);

}