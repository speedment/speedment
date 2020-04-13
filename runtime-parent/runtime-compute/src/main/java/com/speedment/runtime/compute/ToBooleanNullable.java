/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.compute;

import static java.util.Objects.requireNonNull;

import com.speedment.common.function.BooleanToDoubleFunction;
import com.speedment.common.function.BooleanUnaryOperator;
import com.speedment.common.function.ToBooleanFunction;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.OrElseGetUtil;
import com.speedment.runtime.compute.internal.expression.OrElseThrowUtil;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasCompose;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasMapIfPresent;
import com.speedment.runtime.compute.trait.HasMapToDoubleIfPresent;
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
        ToBooleanFunction<T>,
        ToNullable<T, Boolean, ToBoolean<T>>,
        HasMapToDoubleIfPresent<T, BooleanToDoubleFunction>,
        HasMapIfPresent<T, BooleanUnaryOperator, ToBooleanNullable<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    /**
     * Returns a typed {@code ToBooleanNullable<T>} using the provided
     * {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToBooleanNullable<T>} using the provided
     * {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    // Note that Function<T, Boolean> is not the same as Predicate<T>
    // since the former returns Boolean and the later boolean
    static <T> ToBooleanNullable<T> of(Function<T, Boolean> lambda) {
        requireNonNull(lambda);
        if (lambda instanceof ToBooleanNullable) {
            return (ToBooleanNullable<T>) lambda;
        } else {
            return lambda::apply;
        }
    }
    
    @Override
    default ExpressionType expressionType() {
        return ExpressionType.BOOLEAN_NULLABLE;
    }

    @Override
    default boolean applyAsBoolean(T object) {
        return apply(object);
    }

    @Override
    default ToBoolean<T> orThrow() {
        return OrElseThrowUtil.booleanOrElseThrow(this);
    }

    @Override
    default ToBoolean<T> orElseGet(ToBoolean<T> getter) {
        return OrElseGetUtil.booleanOrElseGet(this, getter);
    }

    @Override
    default ToBoolean<T> orElse(Boolean value) {
        return OrElseUtil.booleanOrElse(this, value);
    }

    @Override
    default ToDoubleNullable<T> mapToDoubleIfPresent(BooleanToDoubleFunction mapper) {
        final ToBooleanNullable<T> delegate = this;
        return new ToDoubleNullable<T>() {
            @Override
            public Double apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsDouble(delegate.applyAsBoolean(object));
            }

            @Override
            public double applyAsDouble(T object) {
                return mapper.applyAsDouble(delegate.applyAsBoolean(object));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsDouble(object)
                    : mapper.applyAsDouble(delegate.applyAsBoolean(object));
            }

            @Override
            public ToDouble<T> orElse(Double value) {
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

    @Override
    default ToBooleanNullable<T> mapIfPresent(BooleanUnaryOperator mapper) {
        final ToBooleanNullable<T> delegate = this;
        return new ToBooleanNullable<T>() {
            @Override
            public Boolean apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsBoolean(delegate.applyAsBoolean(object));
            }

            @Override
            public boolean applyAsBoolean(T object) {
                return mapper.applyAsBoolean(delegate.applyAsBoolean(object));
            }

            @Override
            public ToBoolean<T> orElseGet(ToBoolean<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsBoolean(object)
                    : mapper.applyAsBoolean(delegate.applyAsBoolean(object));
            }

            @Override
            public ToBoolean<T> orElse(Boolean value) {
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
        if (isNull(object)) {
            return 0;
        }
        return applyAsBoolean(object) ? 1 : 2;
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
        requireNonNull(before);
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToBooleanNullable(casted, this);
    }
}
