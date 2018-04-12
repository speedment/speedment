package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;

import java.util.function.Function;

/**
 * Expression that given an entity returns a non-null {@code String} value. This
 * expression can be implemented using a lamda, or it can be a result of another
 * operation. It has additional methods for operating on it.
 *
 * @see Function
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToString<T>
extends Expression,
        Function<T, String>,
        HasHash<T>,
        HasCompare<T> {

    @Override
    String apply(T object);

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.STRING;
    }

    @Override
    default long hash(T object) {
        return apply(object).hashCode();
    }

    @Override
    default int compare(T first, T second) {
        return apply(first).compareTo(apply(second));
    }
}
