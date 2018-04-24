package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.expression.Expression;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Trait for expressions that result in a nullable value. Those expressions have
 * additional methods for checking for {@code null}-values.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToNullable<T, R> extends Function<T, R>, Expression<T> {

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

}