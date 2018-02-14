package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code byte} returns
 * another {@code byte}.
 *
 * @see java.util.function.IntUnaryOperator
 * @see java.util.function.LongUnaryOperator
 * @see java.util.function.DoubleUnaryOperator
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface ByteUnaryOperator {

    /**
     * Applies this function on the specified {@code byte}, returning a new
     * {@code byte}.
     *
     * @param value  the input value
     * @return       the output
     */
    byte applyAsByte(byte value);

    /**
     * Composes a new function from this method and the specified one, applying
     * the specified function <em>before</em> this function.
     *
     * @param before  the function to apply <em>before</em> this
     * @return        the combined function
     */
    default ByteUnaryOperator compose(ByteUnaryOperator before) {
        return input -> applyAsByte(before.applyAsByte(input));
    }

    /**
     * Composes a new function from this method and the specified one, applying
     * the specified function <em>after</em> this function.
     *
     * @param after  the function to apply <em>after</em> this
     * @return       the combined function
     */
    default ByteUnaryOperator andThen(ByteUnaryOperator after) {
        return input -> after.applyAsByte(applyAsByte(input));
    }
}
