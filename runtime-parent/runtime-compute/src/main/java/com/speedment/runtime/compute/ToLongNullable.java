package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.Expressions;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.*;

import java.util.function.Function;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongUnaryOperator;

/**
 * Expression that given an entity returns a {@code long} value, or
 * {@code null}. This expression can be implemented using a lamda, or it can be
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
public interface ToLongNullable<T>
    extends Expression,
    ToNullable<T, Long>,
    HasAbs<ToLongNullable<T>>,
    HasSign<ToByteNullable<T>>,
    HasSqrt<ToDoubleNullable<T>>,
    HasNegate<ToLongNullable<T>>,
    HasHash<T>,
    HasCompare<T> {

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.LONG_NULLABLE;
    }

    default long applyAsLong(T object) throws NullPointerException {
        return apply(object);
    }

    default ToLong<T> orThrow() throws NullPointerException {
        return this::apply;
    }

    default ToLong<T> orElseGet(ToLong<T> getter) {
        return object -> isNull(object)
            ? getter.applyAsLong(object)
            : applyAsLong(object);
    }

    default ToLong<T> orElse(long value) {
        return OrElseUtil.orElse(this, value);
    }

    @Override
    default ToLongNullable<T> abs() {
        return Expressions.absOrNull(this);
    }

    @Override
    default ToLongNullable<T> negate() {
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

    default ToDoubleNullable<T> mapToDoubleIfPresent(LongToDoubleFunction mapper) {
        final ToLongNullable<T> delegate = this;
        return new ToDoubleNullable<T>() {
            @Override
            public Double apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsDouble(delegate.applyAsLong(object));
            }

            @Override
            public double applyAsDouble(T object) throws NullPointerException {
                return mapper.applyAsDouble(delegate.applyAsLong(object));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsDouble(object)
                    : mapper.applyAsDouble(delegate.applyAsLong(object));
            }

            @Override
            public ToDouble<T> orElse(double value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsDouble(delegate.applyAsLong(object));
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

    default ToLongNullable<T> mapIfPresent(LongUnaryOperator mapper) {
        final ToLongNullable<T> delegate = this;
        return new ToLongNullable<T>() {
            @Override
            public Long apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsLong(delegate.applyAsLong(object));
            }

            @Override
            public long applyAsLong(T object) throws NullPointerException {
                return mapper.applyAsLong(delegate.applyAsLong(object));
            }

            @Override
            public ToLong<T> orElseGet(ToLong<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsLong(object)
                    : mapper.applyAsLong(delegate.applyAsLong(object));
            }

            @Override
            public ToLong<T> orElse(long value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsLong(delegate.applyAsLong(object));
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
        if (isNull(object)) {
            return 0;
        } else {
            final long l = applyAsLong(object);
            return (int) (l ^ (l >>> 32));
        }
    }

    @Override
    default int compare(T first, T second) {
        if (isNull(first)) {
            return isNull(second) ? 0 : 1;
        } else if (isNull(second)) {
            return -1;
        } else {
            return Long.compare(
                applyAsLong(first),
                applyAsLong(second)
            );
        }
    }
}
