package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToString;
import com.speedment.runtime.compute.expression.Expression;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Trait for expressions that result in a nullable value. Those expressions have
 * additional methods for checking for {@code null}-values.
 *
 * @param <T>  type to extract from
 * @param <R>  return type
 * @param <NON_NULLABLE>  the expression type obtained if the nullability of
 *                        this expression is handled
 * 
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToNullable<T, R, NON_NULLABLE extends Expression<T>>
extends Function<T, R>, Expression<T> {

    /**
     * Returns a {@code Predicate} that is {@code true} for any values that
     * would make this expression return {@code null} and {@code false} for any
     * values that would make this expression return a result.
     *
     * @return  a predicate that tests if this expression would return
     *          {@code null}
     */
    default Predicate<T> isNull() {
        return this::isNull;
    }

    /**
     * Returns a {@code Predicate} that is {@code false} for any values that
     * would make this expression return {@code null} and {@code true} for any
     * values that would make this expression return a result.
     *
     * @return  a predicate that tests if this expression would return something
     *          other than {@code null}
     */
    default Predicate<T> isNotNull() {
        return this::isNotNull;
    }

    /**
     * Returns {@code true} if the specified object would cause this methods
     * {@link #apply(Object)}-method to return {@code null}, and otherwise
     * {@code false}.
     *
     * @param object  the incoming entity to test on
     * @return        {@code true} if the expression would return {@code null},
     *                else {@code false}
     */
    default boolean isNull(T object) {
        return apply(object) == null;
    }

    /**
     * Returns {@code false} if the specified object would cause this methods
     * {@link #apply(Object)}-method to return {@code null}, and otherwise
     * {@code true}.
     *
     * @param object  the incoming entity to test on
     * @return        {@code false} if the expression would return {@code null},
     *                else {@code true}
     */
    default boolean isNotNull(T object) {
        return apply(object) != null;
    }

    /**
     * Returns an equivalent expression as this, except that it will throw a
     * {@code NullPointerException} if given an argument that would cause this
     * expression to return {@code null}.
     *
     * @return
     */
    NON_NULLABLE orThrow();

    NON_NULLABLE orElseGet(NON_NULLABLE getter);

    NON_NULLABLE orElse(R value);

}