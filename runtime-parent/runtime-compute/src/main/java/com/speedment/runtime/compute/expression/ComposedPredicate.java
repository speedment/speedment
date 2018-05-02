package com.speedment.runtime.compute.expression;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Specialized {@link Predicate} that consists of an {@link #innerMapper()} that
 * returns an instance that is then passed to {@link #innerPredicate()} to
 * determine if the element passes the predicate or not.
 *
 * @param <T> initial type
 * @param <A> intermediate type
 * 
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ComposedPredicate<T, A> extends Predicate<T> {

    /**
     * The mapper to apply to an incomming value to get a value that can be
     * passed to the {@link #innerPredicate() inner predicate}.
     *
     * @return  the inner mapper
     */
    Function<T, A> innerMapper();

    /**
     * The predicate to test on the mapped value.
     *
     * @return  the inner predicate
     */
    Predicate<A> innerPredicate();

    /**
     * If this predicate represents a simple {@code a == null} or
     * {@code a != null} predicate on the mapped value, then this method may
     * choose to return a special value so that the predicate may be
     * short-circuited. The method may always return {@link SpecialTypes#OTHER},
     * in which case no such optimization can be done.
     *
     * @return  the type of predicate this represents
     */
    default SpecialTypes specialType() {
        return SpecialTypes.OTHER;
    }

    @Override
    default boolean test(T t) {
        return innerPredicate().test(innerMapper().apply(t));
    }

    /**
     * Special types of predicates that can easily be recognized and potentially
     * short-circuited.
     */
    enum SpecialTypes {

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
}