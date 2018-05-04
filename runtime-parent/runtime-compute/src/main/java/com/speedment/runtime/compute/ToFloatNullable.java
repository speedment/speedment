package com.speedment.runtime.compute;

import com.speedment.common.function.FloatToDoubleFunction;
import com.speedment.common.function.FloatUnaryOperator;
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
 * Expression that given an entity returns a {@code float} value, or
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
public interface ToFloatNullable<T>
extends Expression<T>,
        ToNullable<T, Float>,
        HasAbs<ToFloatNullable<T>>,
        HasSign<ToByteNullable<T>>,
        HasSqrt<ToDoubleNullable<T>>,
        HasNegate<ToFloatNullable<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.FLOAT_NULLABLE;
    }

    default float applyAsFloat(T object) throws NullPointerException {
        return apply(object);
    }

    default ToFloat<T> orThrow() throws NullPointerException {
        return OrElseThrowUtil.orElseThrow(this);
    }

    default ToFloat<T> orElseGet(ToFloat<T> getter) {
        return OrElseGetUtil.orElseGet(this, getter);
    }

    default ToFloat<T> orElse(float value) {
        return OrElseUtil.orElse(this, value);
    }

    @Override
    default ToFloatNullable<T> abs() {
        return Expressions.absOrNull(this);
    }

    @Override
    default ToFloatNullable<T> negate() {
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

    default ToDoubleNullable<T> mapToDoubleIfPresent(FloatToDoubleFunction mapper) {
        final ToFloatNullable<T> delegate = this;
        return new ToDoubleNullable<T>() {
            @Override
            public Double apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsDouble(delegate.applyAsFloat(object));
            }

            @Override
            public double applyAsDouble(T object) throws NullPointerException {
                return mapper.applyAsDouble(delegate.applyAsFloat(object));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsDouble(object)
                    : mapper.applyAsDouble(delegate.applyAsFloat(object));
            }

            @Override
            public ToDouble<T> orElse(double value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsDouble(delegate.applyAsFloat(object));
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

    default ToFloatNullable<T> mapIfPresent(FloatUnaryOperator mapper) {
        final ToFloatNullable<T> delegate = this;
        return new ToFloatNullable<T>() {
            @Override
            public Float apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsFloat(delegate.applyAsFloat(object));
            }

            @Override
            public float applyAsFloat(T object) throws NullPointerException {
                return mapper.applyAsFloat(delegate.applyAsFloat(object));
            }

            @Override
            public ToFloat<T> orElseGet(ToFloat<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsFloat(object)
                    : mapper.applyAsFloat(delegate.applyAsFloat(object));
            }

            @Override
            public ToFloat<T> orElse(float value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsFloat(delegate.applyAsFloat(object));
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
            final float f = applyAsFloat(object);
            return f != +0.0f ? Float.floatToIntBits(f) : 0;
        }
    }

    @Override
    default int compare(T first, T second) {
        if (isNull(first)) {
            return isNull(second) ? 0 : 1;
        } else if (isNull(second)) {
            return -1;
        } else {
            return Float.compare(
                applyAsFloat(first),
                applyAsFloat(second)
            );
        }
    }

    @Override
    default <V> ToFloatNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeNullable(casted, this);
    }
}
