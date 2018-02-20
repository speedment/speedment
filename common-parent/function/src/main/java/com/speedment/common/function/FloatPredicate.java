package com.speedment.common.function;

/**
 * Predicate that tests a primitive {@code float}.
 *
 * @see java.util.function.Predicate
 * @see java.util.function.IntPredicate
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface FloatPredicate {

    boolean test(float value);

}
