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

    default Predicate<T> isNull() {
        return this::isNull;
    }

    default Predicate<T> isNotNull() {
        return this::isNotNull;
    }

    default boolean isNull(T object) {
        return apply(object) == null;
    }

    default boolean isNotNull(T object) {
        return apply(object) != null;
    }

    NON_NULLABLE orThrow() throws NullPointerException;

    NON_NULLABLE orElseGet(NON_NULLABLE getter);

    NON_NULLABLE orElse(R value);

}