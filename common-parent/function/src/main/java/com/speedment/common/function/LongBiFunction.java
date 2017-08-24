package com.speedment.common.function;

/**
 * Function that takes two primitive {@code longs} and returns an object.
 *
 * @author Emil Forslund
 * @since  1.0.3
 */
@FunctionalInterface
public interface LongBiFunction<U> {

    /**
     * Takes two primitive {@code long}-values and returns an object.
     *
     * @param first   the first {@code long}
     * @param second  the first {@code long}
     * @return        the result
     */
    U apply(long first, long second);

}
