package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code char} returns
 * another {@code char}.
 *
 * @see java.util.function.IntUnaryOperator
 * @see java.util.function.LongUnaryOperator
 * @see java.util.function.DoubleUnaryOperator
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface CharUnaryOperator {

    /**
     * Applies this function on the specified {@code char}, returning a new
     * {@code char}.
     *
     * @param value  the input value
     * @return       the output
     */
    char applyAsChar(char value);

    /**
     * Composes a new function from this method and the specified one, applying
     * the specified function <em>before</em> this function.
     *
     * @param before  the function to apply <em>before</em> this
     * @return        the combined function
     */
    default CharUnaryOperator compose(CharUnaryOperator before) {
        return input -> applyAsChar(before.applyAsChar(input));
    }

    /**
     * Composes a new function from this method and the specified one, applying
     * the specified function <em>after</em> this function.
     *
     * @param after  the function to apply <em>after</em> this
     * @return       the combined function
     */
    default CharUnaryOperator andThen(CharUnaryOperator after) {
        return input -> after.applyAsChar(applyAsChar(input));
    }
}
