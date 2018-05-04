package com.speedment.runtime.compute;

import com.speedment.common.function.BooleanToDoubleFunction;
import com.speedment.common.function.BooleanUnaryOperator;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.OrElseGetUtil;
import com.speedment.runtime.compute.internal.expression.OrElseThrowUtil;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasCompose;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.ToNullable;

import java.util.function.Function;

/**
 * Expression that given an entity returns a {@code boolean} value, or
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
public interface ToBooleanNullable<T>
extends Expression<T>,
        ToNullable<T, Boolean>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.BOOLEAN_NULLABLE;
    }

    default boolean applyAsBoolean(T object) throws NullPointerException {
        return apply(object);
    }

    default ToBoolean<T> orThrow() throws NullPointerException {
        return OrElseThrowUtil.orElseThrow(this);
    }

    default ToBoolean<T> orElseGet(ToBoolean<T> getter) {
        return OrElseGetUtil.orElseGet(this, getter);
    }

    default ToBoolean<T> orElse(boolean value) {
        return OrElseUtil.orElse(this, value);
    }

    default ToDoubleNullable<T> mapToDoubleIfPresent(BooleanToDoubleFunction mapper) {
        final ToBooleanNullable<T> delegate = this;
        return new ToDoubleNullable<T>() {
            @Override
            public Double apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsDouble(delegate.applyAsBoolean(object));
            }

            @Override
            public double applyAsDouble(T object) throws NullPointerException {
                return mapper.applyAsDouble(delegate.applyAsBoolean(object));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsDouble(object)
                    : mapper.applyAsDouble(delegate.applyAsBoolean(object));
            }

            @Override
            public ToDouble<T> orElse(double value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsDouble(delegate.applyAsBoolean(object));
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

    default ToBooleanNullable<T> mapIfPresent(BooleanUnaryOperator mapper) {
        final ToBooleanNullable<T> delegate = this;
        return new ToBooleanNullable<T>() {
            @Override
            public Boolean apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsBoolean(delegate.applyAsBoolean(object));
            }

            @Override
            public boolean applyAsBoolean(T object) throws NullPointerException {
                return mapper.applyAsBoolean(delegate.applyAsBoolean(object));
            }

            @Override
            public ToBoolean<T> orElseGet(ToBoolean<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsBoolean(object)
                    : mapper.applyAsBoolean(delegate.applyAsBoolean(object));
            }

            @Override
            public ToBoolean<T> orElse(boolean value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsBoolean(delegate.applyAsBoolean(object));
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
        return isNull(object) ? 0 : (applyAsBoolean(object) ? 1 : 2);
    }

    @Override
    default int compare(T first, T second) {
        if (isNull(first)) {
            return isNull(second) ? 0 : 1;
        } else if (isNull(second)) {
            return -1;
        } else {
            return Boolean.compare(
                applyAsBoolean(first),
                applyAsBoolean(second)
            );
        }
    }

    @Override
    default <V> ToBooleanNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeNullable(casted, this);
    }
}
