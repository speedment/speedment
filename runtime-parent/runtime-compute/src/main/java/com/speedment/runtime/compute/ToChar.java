package com.speedment.runtime.compute;

import com.speedment.common.function.ToCharFunction;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.trait.HasAsDouble;
import com.speedment.runtime.compute.trait.HasAsInt;
import com.speedment.runtime.compute.trait.HasAsLong;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;

/**
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToChar<T>
extends Expression,
        ToCharFunction<T>,
        HasAsDouble<T>,
        HasAsInt<T>,
        HasAsLong<T>,
        HasHash<T>,
        HasCompare<T> {

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.CHAR;
    }

    @Override
    default ToDouble<T> asDouble() {
        return this::applyAsChar;
    }

    @Override
    default ToInt<T> asInt() {
        return this::applyAsChar;
    }

    @Override
    default ToLong<T> asLong() {
        return this::applyAsChar;
    }

    @Override
    default long hash(T object) {
        return applyAsChar(object);
    }

    @Override
    default int compare(T first, T second) {
        final char f = applyAsChar(first);
        final char s = applyAsChar(second);
        return Character.compare(f, s);
    }
}
