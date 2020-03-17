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
import java.util.function.LongToDoubleFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ToLongFunction;

import static java.util.Objects.requireNonNull;

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
extends Expression<T>,
        ToNullable<T, Long, ToLong<T>>,
        ToLongFunction<T>,
        HasAbs<ToLongNullable<T>>,
        HasSign<ToByteNullable<T>>,
        HasSqrt<ToDoubleNullable<T>>,
        HasNegate<ToLongNullable<T>>,
        HasMapToDoubleIfPresent<T, LongToDoubleFunction>,
        HasMapIfPresent<T, LongUnaryOperator, ToLongNullable<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    /**
     * Returns a typed {@code ToLongNullable<T>} using the provided
     * {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToLongNullable<T>} using the provided
     * {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    // Note that Function<T, Long> is not the same as ToLongFunction<T>
    // since the former returns Long and the later long
    static <T> ToLongNullable<T> of(Function<T, Long> lambda) {
        requireNonNull(lambda);
        if (lambda instanceof ToLongNullable) {
            return (ToLongNullable<T>) lambda;
        } else {
            return lambda::apply;
        }
    }

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.LONG_NULLABLE;
    }

    @Override
    default long applyAsLong(T object) {
        return apply(object);
    }

    @Override
    default ToLong<T> orThrow() {
        return OrElseThrowUtil.longOrElseThrow(this);
    }

    @Override
    default ToLong<T> orElseGet(ToLong<T> getter) {
        return OrElseGetUtil.longOrElseGet(this, getter);
    }

    @Override
    default ToLong<T> orElse(Long value) {
        return OrElseUtil.longOrElse(this, value);
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

    @Override
    default ToDoubleNullable<T> mapToDoubleIfPresent(LongToDoubleFunction mapper) {
        final ToLongNullable<T> delegate = this;
        return new ToDoubleNullable<T>() {
            @Override
            public Double apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsDouble(delegate.applyAsLong(object));
            }

            @Override
            public double applyAsDouble(T object) {
                return mapper.applyAsDouble(delegate.applyAsLong(object));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsDouble(object)
                    : mapper.applyAsDouble(delegate.applyAsLong(object));
            }

            @Override
            public ToDouble<T> orElse(Double value) {
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

    @Override
    default ToLongNullable<T> mapIfPresent(LongUnaryOperator mapper) {
        final ToLongNullable<T> delegate = this;
        return new ToLongNullable<T>() {
            @Override
            public Long apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsLong(delegate.applyAsLong(object));
            }

            @Override
            public long applyAsLong(T object) {
                return mapper.applyAsLong(delegate.applyAsLong(object));
            }

            @Override
            public ToLong<T> orElseGet(ToLong<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsLong(object)
                    : mapper.applyAsLong(delegate.applyAsLong(object));
            }

            @Override
            public ToLong<T> orElse(Long value) {
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

    @Override
    default <V> ToLongNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToLongNullable(casted, this);
    }
}
