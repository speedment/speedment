package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.OrElseGetUtil;
import com.speedment.runtime.compute.internal.expression.OrElseThrowUtil;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.ToNullable;

import java.util.function.Function;

/**
 * Expression that given an entity returns a {@code char} value, or
 * {@code null}. This expression can be implemented using a lambda, or it can be
 * a result of another operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 *
 * @see Function
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToCharNullable<T>
extends Expression<T>,
        ToNullable<T, Character>,
        HasHash<T>,
        HasCompare<T> {

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.CHAR_NULLABLE;
    }

    default char applyAsChar(T object) throws NullPointerException {
        return apply(object);
    }

    default ToChar<T> orThrow() throws NullPointerException {
        return OrElseThrowUtil.orElseThrow(this);
    }

    default ToChar<T> orElseGet(ToChar<T> getter) {
        return OrElseGetUtil.orElseGet(this, getter);
    }

    default ToChar<T> orElse(char value) {
        return OrElseUtil.orElse(this, value);
    }

    @Override
    default long hash(T object) {
        return isNull(object) ? 0 : applyAsChar(object);
    }

    @Override
    default int compare(T first, T second) {
        if (isNull(first)) {
            return isNull(second) ? 0 : 1;
        } else if (isNull(second)) {
            return -1;
        } else {
            return Character.compare(
                applyAsChar(first),
                applyAsChar(second)
            );
        }
    }
}
