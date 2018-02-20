package com.speedment.common.function;

/**
 * Predicate that tests a primitive {@code byte}.
 *
 * @see java.util.function.Predicate
 * @see java.util.function.IntPredicate
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface BytePredicate {

    boolean test(byte value);

}
