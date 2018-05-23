package com.speedment.runtime.compute.expression.predicate;

/**
 * Special types of predicates that can easily be recognized and potentially
 * short-circuited.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public enum NullPredicateType {

    /**
     * Represents a simply {@code a -> a == null} predicate.
     */
    IS_NULL,

    /**
     * Represents a simply {@code a -> a != null} predicate.
     */
    IS_NOT_NULL,

    /**
     * Represents any kind of predicate, including {@link #IS_NULL} or
     * {@link #IS_NOT_NULL}.
     */
    OTHER
}
