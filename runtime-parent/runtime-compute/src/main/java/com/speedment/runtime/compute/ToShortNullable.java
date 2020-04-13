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

import com.speedment.common.function.ShortToDoubleFunction;
import com.speedment.common.function.ShortUnaryOperator;
import com.speedment.common.function.ToShortFunction;
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
        ToShortFunction<T>,
        ToNullable<T, Short, ToShort<T>>,
        HasAbs<ToShortNullable<T>>,
        HasSign<ToByteNullable<T>>,
        HasSqrt<ToDoubleNullable<T>>,
        HasNegate<ToShortNullable<T>>,
        HasMapToDoubleIfPresent<T, ShortToDoubleFunction>,
        HasMapIfPresent<T, ShortUnaryOperator, ToShortNullable<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    /**
     * Returns a typed {@code ToShortNullable<T>} using the provided
     * {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToShortNullable<T>} using the provided
     * {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T> ToShortNullable<T> of(Function<T, Short> lambda) {
        if (lambda instanceof ToShortNullable) {
            return (ToShortNullable<T>) lambda;
        } else {
            return lambda::apply;
        }
    }
    
    @Override
    default ExpressionType expressionType() {
        return ExpressionType.SHORT_NULLABLE;
    }

    @Override
    default short applyAsShort(T object) {
        return apply(object);
    }

    @Override
    default ToShort<T> orThrow() {
        return OrElseThrowUtil.shortOrElseThrow(this);
    }

    @Override
    default ToShort<T> orElseGet(ToShort<T> getter) {
        return OrElseGetUtil.shortOrElseGet(this, getter);
    }

    @Override
    default ToShort<T> orElse(Short value) {
        return OrElseUtil.shortOrElse(this, value);
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

    @Override
    default ToDoubleNullable<T> mapToDoubleIfPresent(ShortToDoubleFunction mapper) {
        final ToShortNullable<T> delegate = this;
        return new ToDoubleNullable<T>() {
            @Override
            public Double apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsDouble(delegate.applyAsShort(object));
            }

            @Override
            public double applyAsDouble(T object) {
                return mapper.applyAsDouble(delegate.applyAsShort(object));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsDouble(object)
                    : mapper.applyAsDouble(delegate.applyAsShort(object));
            }

            @Override
            public ToDouble<T> orElse(Double value) {
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

    @Override
    default ToShortNullable<T> mapIfPresent(ShortUnaryOperator mapper) {
        final ToShortNullable<T> delegate = this;
        return new ToShortNullable<T>() {
            @Override
            public Short apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsShort(delegate.applyAsShort(object));
            }

            @Override
            public short applyAsShort(T object) {
                return mapper.applyAsShort(delegate.applyAsShort(object));
            }

            @Override
            public ToShort<T> orElseGet(ToShort<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsShort(object)
                    : mapper.applyAsShort(delegate.applyAsShort(object));
            }

            @Override
            public ToShort<T> orElse(Short value) {
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
    default <V> ToShortNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToShortNullable(casted, this);
    }
}
