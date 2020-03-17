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

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.Expressions;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.OrElseGetUtil;
import com.speedment.runtime.compute.internal.expression.OrElseThrowUtil;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.*;

import java.util.function.Function;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;

import static java.util.Objects.requireNonNull;

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
        ToIntFunction<T>,
        ToNullable<T, Integer, ToInt<T>>,
        HasAbs<ToIntNullable<T>>,
        HasSign<ToByteNullable<T>>,
        HasSqrt<ToDoubleNullable<T>>,
        HasNegate<ToIntNullable<T>>,
        HasMapToDoubleIfPresent<T, IntToDoubleFunction>,
        HasMapIfPresent<T, IntUnaryOperator, ToIntNullable<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    
    /**
     * Returns a typed {@code ToIntNullable<T>} using the provided
     * {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToIntNullable<T>} using the provided
     * {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    // Note that Function<T, Integer> is not the same as ToIntFunction<T>
    // since the former returns Integer and the later int
    static <T> ToIntNullable<T> of(Function<T, Integer> lambda) {
        requireNonNull(lambda);
        if (lambda instanceof ToIntNullable) {
            return (ToIntNullable<T>) lambda;
        } else {
            return lambda::apply;
        }
    }
    
    @Override
    default ExpressionType expressionType() {
        return ExpressionType.INT_NULLABLE;
    }

    @Override
    default int applyAsInt(T object) {
        return apply(object);
    }

    @Override
    default ToInt<T> orThrow() {
        return OrElseThrowUtil.intOrElseThrow(this);
    }

    @Override
    default ToInt<T> orElseGet(ToInt<T> getter) {
        return OrElseGetUtil.intOrElseGet(this, getter);
    }

    @Override
    default ToInt<T> orElse(Integer value) {
        return OrElseUtil.intOrElse(this, value);
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

    @Override
    default ToDoubleNullable<T> mapToDoubleIfPresent(IntToDoubleFunction mapper) {
        final ToIntNullable<T> delegate = this;
        return new ToDoubleNullable<T>() {
            @Override
            public Double apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsDouble(delegate.applyAsInt(object));
            }

            @Override
            public double applyAsDouble(T object) {
                return mapper.applyAsDouble(delegate.applyAsInt(object));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsDouble(object)
                    : mapper.applyAsDouble(delegate.applyAsInt(object));
            }

            @Override
            public ToDouble<T> orElse(Double value) {
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

    @Override
    default ToIntNullable<T> mapIfPresent(IntUnaryOperator mapper) {
        final ToIntNullable<T> delegate = this;
        return new ToIntNullable<T>() {
            @Override
            public Integer apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsInt(delegate.applyAsInt(object));
            }

            @Override
            public int applyAsInt(T object) {
                return mapper.applyAsInt(delegate.applyAsInt(object));
            }

            @Override
            public ToInt<T> orElseGet(ToInt<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsInt(object)
                    : mapper.applyAsInt(delegate.applyAsInt(object));
            }

            @Override
            public ToInt<T> orElse(Integer value) {
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

    @Override
    default <V> ToIntNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToIntNullable(casted, this);
    }
}
