package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code short} returns
 * another {@code short}.
 *
 * @see java.util.function.IntUnaryOperator
 * @see java.util.function.LongUnaryOperator
 * @see java.util.function.DoubleUnaryOperator
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface ShortUnaryOperator {

    /**
     * Applies this function on the specified {@code short}, returning a new
     * {@code short}.
     *
     * @param value  the input value
     * @return       the output
     */
    short applyAsShort(short value);

    /**
     * Composes a new function from this method and the specified one, applying
     * the specified function <em>before</em> this function.
     *
     * @param before  the function to apply <em>before</em> this
     * @return        the combined function
     */
    default ShortUnaryOperator compose(ShortUnaryOperator before) {
        return input -> applyAsShort(before.applyAsShort(input));
    }

    /**
     * Composes a new function from this method and the specified one, applying
     * the specified function <em>after</em> this function.
     *
     * @param after  the function to apply <em>after</em> this
     * @return       the combined function
     */
    default ShortUnaryOperator andThen(ShortUnaryOperator after) {
        return input -> after.applyAsShort(applyAsShort(input));
    }
}
