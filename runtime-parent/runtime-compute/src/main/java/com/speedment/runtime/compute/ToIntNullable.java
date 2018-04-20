package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.Expressions;
import com.speedment.runtime.compute.internal.expression.OrElseGetUtil;
import com.speedment.runtime.compute.internal.expression.OrElseThrowUtil;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.*;

import java.util.function.Function;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;

/**
 * Expression that given an entity returns an {@code int} value, or
 * {@code null}. This expression can be implemented using a lamdda, or it can be
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
public interface ToIntNullable<T>
extends Expression<T>,
        ToNullable<T, Integer>,
        HasAbs<ToIntNullable<T>>,
        HasSign<ToByteNullable<T>>,
        HasSqrt<ToDoubleNullable<T>>,
        HasNegate<ToIntNullable<T>>,
        HasHash<T>,
        HasCompare<T> {

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.INT_NULLABLE;
    }

    default int applyAsInt(T object) throws NullPointerException {
        return apply(object);
    }

    default ToInt<T> orThrow() throws NullPointerException {
        return OrElseThrowUtil.orElseThrow(this);
    }

    default ToInt<T> orElseGet(ToInt<T> getter) {
        return OrElseGetUtil.orElseGet(this, getter);
    }

    default ToInt<T> orElse(int value) {
        return OrElseUtil.orElse(this, value);
    }

    @Override
    default ToIntNullable<T> abs() {
        return Expressions.absOrNull(this);
    }

    @Override
    default ToIntNullable<T> negate() {
        return Expressions.negateOrNull(this);
    }

    @Override
    default ToByteNullable<T> sign() {
        return Expressions.signOrNull(this);
    }

    @Override
    default ToDoubleNullable<T> sqrt() {
        return Expressions.sqrtOrNull(this);
    }

    default ToDoubleNullable<T> mapToDoubleIfPresent(IntToDoubleFunction mapper) {
        final ToIntNullable<T> delegate = this;
        return new ToDoubleNullable<T>() {
            @Override
            public Double apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsDouble(delegate.applyAsInt(object));
            }

            @Override
            public double applyAsDouble(T object) throws NullPointerException {
                return mapper.applyAsDouble(delegate.applyAsInt(object));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsDouble(object)
                    : mapper.applyAsDouble(delegate.applyAsInt(object));
            }

            @Override
            public ToDouble<T> orElse(double value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsDouble(delegate.applyAsInt(object));
            }

            @Override
            public boolean isNull(T object) {
                return delegate.isNull(object);
            }

            @Override
            public boolean isNotNull(T object) {
                return delegate.isNotNull(object);
            }
        };
    }

    default ToIntNullable<T> mapIfPresent(IntUnaryOperator mapper) {
        final ToIntNullable<T> delegate = this;
        return new ToIntNullable<T>() {
            @Override
            public Integer apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsInt(delegate.applyAsInt(object));
            }

            @Override
            public int applyAsInt(T object) throws NullPointerException {
                return mapper.applyAsInt(delegate.applyAsInt(object));
            }

            @Override
            public ToInt<T> orElseGet(ToInt<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsInt(object)
                    : mapper.applyAsInt(delegate.applyAsInt(object));
            }

            @Override
            public ToInt<T> orElse(int value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsInt(delegate.applyAsInt(object));
            }

            @Override
            public boolean isNull(T object) {
                return delegate.isNull(object);
            }

            @Override
            public boolean isNotNull(T object) {
                return delegate.isNotNull(object);
            }
        };
    }

    @Override
    default long hash(T object) {
        return isNull(object) ? 0 : applyAsInt(object);
    }

    @Override
    default int compare(T first, T second) {
        if (isNull(first)) {
            return isNull(second) ? 0 : 1;
        } else if (isNull(second)) {
            return -1;
        } else {
            return Integer.compare(
                applyAsInt(first),
                applyAsInt(second)
            );
        }
    }
}
