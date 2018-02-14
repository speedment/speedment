package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code boolean} returns
 * another {@code boolean}.
 *
 * @see java.util.function.IntUnaryOperator
 * @see java.util.function.LongUnaryOperator
 * @see java.util.function.DoubleUnaryOperator
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface BooleanUnaryOperator {

    /**
     * Applies this function on the specified {@code boolean}, returning a new
     * {@code boolean}.
     *
     * @param value  the input value
     * @return       the output
     */
    boolean applyAsBoolean(boolean value);

    /**
     * Composes a new function from this method and the specified one, applying
     * the specified function <em>before</em> this function.
     *
     * @param before  the function to apply <em>before</em> this
     * @return        the combined function
     */
    default BooleanUnaryOperator compose(BooleanUnaryOperator before) {
        return input -> applyAsBoolean(before.applyAsBoolean(input));
    }

    /**
     * Composes a new function from this method and the specified one, applying
     * the specified function <em>after</em> this function.
     *
     * @param after  the function to apply <em>after</em> this
     * @return       the combined function
     */
    default BooleanUnaryOperator andThen(BooleanUnaryOperator after) {
        return input -> after.applyAsBoolean(applyAsBoolean(input));
    }
}
