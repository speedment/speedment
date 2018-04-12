package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;

import java.util.function.Function;

/**
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
