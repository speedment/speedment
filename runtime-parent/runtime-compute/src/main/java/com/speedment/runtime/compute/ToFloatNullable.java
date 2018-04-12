package com.speedment.runtime.compute;

import com.speedment.common.function.FloatToDoubleFunction;
import com.speedment.common.function.FloatUnaryOperator;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.Expressions;
import com.speedment.runtime.compute.trait.HasAbs;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasNegate;
import com.speedment.runtime.compute.trait.HasSign;
import com.speedment.runtime.compute.trait.HasSqrt;
import com.speedment.runtime.compute.trait.ToNullable;

/**
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToFloatNullable<T>
extends Expression,
        ToNullable<T, Float>,
        HasAbs<ToFloatNullable<T>>,
        HasSign<ToByteNullable<T>>,
        HasSqrt<ToDoubleNullable<T>>,
        HasNegate<ToFloatNullable<T>>,
        HasHash<T>,
        HasCompare<T> {

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.FLOAT_NULLABLE;
    }

    default float applyAsFloat(T object) throws NullPointerException {
        return apply(object);
    }

    default ToFloat<T> orThrow() throws NullPointerException {
        return this::apply;
    }

    default ToFloat<T> orElseGet(ToFloat<T> getter) {
        return object -> isNull(object)
            ? getter.applyAsFloat(object)
            : applyAsFloat(object);
    }

    default ToFloat<T> orElse(float value) {
        return object -> isNull(object)
            ? value
            : applyAsFloat(object);
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
}
