package com.speedment.runtime.compute;

import com.speedment.common.function.ShortToDoubleFunction;
import com.speedment.common.function.ShortUnaryOperator;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.Expressions;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.OrElseGetUtil;
import com.speedment.runtime.compute.internal.expression.OrElseThrowUtil;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.*;

import java.util.function.Function;

/**
 * Expression that given an entity returns a {@code short} value, or
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
public interface ToShortNullable<T>
extends Expression<T>,
        ToNullable<T, Short>,
        HasAbs<ToShortNullable<T>>,
        HasSign<ToByteNullable<T>>,
        HasSqrt<ToDoubleNullable<T>>,
        HasNegate<ToShortNullable<T>>,
        HasHash<T>,
        HasCompare<T> {

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.SHORT_NULLABLE;
    }

    default short applyAsShort(T object) throws NullPointerException {
        return apply(object);
    }

    default ToShort<T> orThrow() throws NullPointerException {
        return OrElseThrowUtil.orElseThrow(this);
    }

    default ToShort<T> orElseGet(ToShort<T> getter) {
        return OrElseGetUtil.orElseGet(this, getter);
    }

    default ToShort<T> orElse(short value) {
        return OrElseUtil.orElse(this, value);
    }

    @Override
    default ToShortNullable<T> abs() {
        return Expressions.absOrNull(this);
    }

    @Override
    default ToShortNullable<T> negate() {
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

    default ToDoubleNullable<T> mapToDoubleIfPresent(ShortToDoubleFunction mapper) {
        final ToShortNullable<T> delegate = this;
        return new ToDoubleNullable<T>() {
            @Override
            public Double apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsDouble(delegate.applyAsShort(object));
            }

            @Override
            public double applyAsDouble(T object) throws NullPointerException {
                return mapper.applyAsDouble(delegate.applyAsShort(object));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsDouble(object)
                    : mapper.applyAsDouble(delegate.applyAsShort(object));
            }

            @Override
            public ToDouble<T> orElse(double value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsDouble(delegate.applyAsShort(object));
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

    default ToShortNullable<T> mapIfPresent(ShortUnaryOperator mapper) {
        final ToShortNullable<T> delegate = this;
        return new ToShortNullable<T>() {
            @Override
            public Short apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsShort(delegate.applyAsShort(object));
            }

            @Override
            public short applyAsShort(T object) throws NullPointerException {
                return mapper.applyAsShort(delegate.applyAsShort(object));
            }

            @Override
            public ToShort<T> orElseGet(ToShort<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsShort(object)
                    : mapper.applyAsShort(delegate.applyAsShort(object));
            }

            @Override
            public ToShort<T> orElse(short value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsShort(delegate.applyAsShort(object));
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
        return isNull(object) ? 0 : applyAsShort(object);
    }

    @Override
    default int compare(T first, T second) {
        if (isNull(first)) {
            return isNull(second) ? 0 : 1;
        } else if (isNull(second)) {
            return -1;
        } else {
            return Short.compare(
                applyAsShort(first),
                applyAsShort(second)
            );
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    default <V> ToShortNullable<V> compose(Function<? super V, ? extends T> before) {
        return ComposedUtil.composeNullable((Function<V, T>) before, this);
    }
}
