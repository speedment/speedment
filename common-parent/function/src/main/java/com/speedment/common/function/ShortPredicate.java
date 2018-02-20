package com.speedment.common.function;

/**
 * Predicate that tests a primitive {@code short}.
 *
 * @see java.util.function.Predicate
 * @see java.util.function.IntPredicate
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface ShortPredicate {

    boolean test(short value);

}
