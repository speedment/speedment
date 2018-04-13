package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.OrElseGetUtil;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.ToNullable;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Expression that given an entity returns a {@code String} value, or
 * {@code null}. This expression can be implemented using a lambda, or it can be
 * a result of another operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 * @see Function
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToStringNullable<T>
    extends Expression,
    ToNullable<T, String>,
    HasHash<T>,
    HasCompare<T> {

    @Override
    String apply(T object);

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.STRING_NULLABLE;
    }

    default ToString<T> orThrow() throws NullPointerException {
        return object -> requireNonNull(apply(object));
    }

    default ToString<T> orElseGet(ToString<T> getter) {
        return OrElseGetUtil.orElseGet(this, getter);
    }

    default ToString<T> orElse(String value) {
        return OrElseUtil.orElse(this, value);
    }

    @Override
    default long hash(T object) {
        return isNull(object) ? 0 : apply(object).hashCode();
    }

    @Override
    default int compare(T first, T second) {
        if (isNull(first)) {
            return isNull(second) ? 0 : 1;
        } else if (isNull(second)) {
            return -1;
        } else {
            return apply(first).compareTo(apply(second));
        }
    }
}
