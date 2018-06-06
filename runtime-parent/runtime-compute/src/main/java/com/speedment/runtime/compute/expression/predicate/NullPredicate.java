package com.speedment.runtime.compute.expression.predicate;

import com.speedment.runtime.compute.trait.ToNullable;

import java.util.function.Predicate;

/**
 * Specialized predicate that holds additional metadata about the condition that
 * can be used to optimize the expression.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface NullPredicate<T, R>
extends Predicate<T> {

    /**
     * If this predicate represents a simple {@code a == null} or
     * {@code a != null} predicate on the mapped value, then this method may
     * choose to return a special value so that the predicate may be
     * short-circuited. The method may always return
     * {@link NullPredicateType#OTHER}, in which case no such optimization
     * can be done.
     *
     * @return  the type of predicate this represents
     */
    NullPredicateType nullPredicateType();

    /**
     * The expression that is invoked in an incoming entity to get the value
     * that the predicate should test.
     *
     * @return  the expression
     */
    ToNullable<T, R, ?> expression();

}
