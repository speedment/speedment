package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code float} returns
 * another {@code float}.
 *
 * @see java.util.function.IntUnaryOperator
 * @see java.util.function.LongUnaryOperator
 * @see java.util.function.DoubleUnaryOperator
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface FloatUnaryOperator {

    /**
     * Applies this function on the specified {@code float}, returning a new
     * {@code float}.
     *
     * @param value  the input value
     * @return       the output
     */
    float applyAsFloat(float value);

    /**
     * Composes a new function from this method and the specified one, applying
     * the specified function <em>before</em> this function.
     *
     * @param before  the function to apply <em>before</em> this
     * @return        the combined function
     */
    default FloatUnaryOperator compose(FloatUnaryOperator before) {
        return input -> applyAsFloat(before.applyAsFloat(input));
    }

    /**
     * Composes a new function from this method and the specified one, applying
     * the specified function <em>after</em> this function.
     *
     * @param after  the function to apply <em>after</em> this
     * @return       the combined function
     */
    default FloatUnaryOperator andThen(FloatUnaryOperator after) {
        return input -> after.applyAsFloat(applyAsFloat(input));
    }
}
