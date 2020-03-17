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
import com.speedment.runtime.compute.trait.HasAbs;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasCompose;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasMapIfPresent;
import com.speedment.runtime.compute.trait.HasNegate;
import com.speedment.runtime.compute.trait.HasSign;
import com.speedment.runtime.compute.trait.HasSqrt;
import com.speedment.runtime.compute.trait.ToNullable;

import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import static java.util.Objects.requireNonNull;

/**
 * Expression that given an entity returns a {@code double} value, or
 * {@code null}. This expression can be implemented using a lambda, or it can be
 * a result of another operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 *
 * @see Function
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
@FunctionalInterface
public interface ToDoubleNullable<T>
extends Expression<T>,
        ToDoubleFunction<T>,
        ToNullable<T, Double, ToDouble<T>>,
        HasAbs<ToDoubleNullable<T>>,
        HasSign<ToByteNullable<T>>,
        HasSqrt<ToDoubleNullable<T>>,
        HasNegate<ToDoubleNullable<T>>,
        HasMapIfPresent<T, DoubleUnaryOperator, ToDoubleNullable<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    /**
     * Returns a typed {@code ToDoubleNullable<T>} using the provided
     * {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToDoubleNullable<T>} using the provided
     * {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    // Note that Function<T, Double> is not the same as ToDoubleFunction<T>
    // since the former returns Double and the later double
    static <T> ToDoubleNullable<T> of(Function<T, Double> lambda) {
        requireNonNull(lambda);
        if (lambda instanceof ToDoubleNullable) {
            return (ToDoubleNullable<T>) lambda;
        } else {
            return lambda::apply;
        }
    }
    
    @Override
    default ExpressionType expressionType() {
        return ExpressionType.DOUBLE_NULLABLE;
    }

    @Override
    default double applyAsDouble(T object) {
        return apply(object);
    }

    @Override
    default ToDouble<T> orThrow() {
        return OrElseThrowUtil.doubleOrElseThrow(this);
    }

    @Override
    default ToDouble<T> orElseGet(ToDouble<T> getter) {
        return OrElseGetUtil.doubleOrElseGet(this, getter);
    }

    @Override
    default ToDouble<T> orElse(Double value) {
        return OrElseUtil.doubleOrElse(this, value);
    }

    @Override
    default ToDoubleNullable<T> abs() {
        return Expressions.absOrNull(this);
    }

    @Override
    default ToDoubleNullable<T> negate() {
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
    default ToDoubleNullable<T> mapIfPresent(DoubleUnaryOperator operator) {
        final ToDoubleNullable<T> delegate = this;
        return new ToDoubleNullable<T>() {

            @Override
            public Double apply(T t) {
                return delegate.isNull(t)
                    ? null
                    : operator.applyAsDouble(delegate.applyAsDouble(t));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return t -> delegate.isNull(t)
                    ? getter.applyAsDouble(t)
                    : operator.applyAsDouble(delegate.applyAsDouble(t));
            }

            @Override
            public ToDouble<T> orElse(Double value) {
                return t -> delegate.isNull(t) ? value
                    : operator.applyAsDouble(delegate.applyAsDouble(t));
            }

            @Override
            public boolean isNull(T object) {
                return delegate.isNull(object);
            }

            @Override
            public boolean isNotNull(T object) {
                return delegate.isNotNull(object);
            }

            @Override
            public ToDoubleNullable<T> mapIfPresent(DoubleUnaryOperator nextOperator) {
                return delegate.mapIfPresent(operator.andThen(nextOperator));
            }
        };
    }

    @Override
    default long hash(T object) {
        if (isNull(object)) {
            return 0;
        } else {
            final long l = Double.doubleToLongBits(applyAsDouble(object));
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
            return Double.compare(
                applyAsDouble(first),
                applyAsDouble(second)
            );
        }
    }

    @Override
    default <V> ToDoubleNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToDoubleNullable(casted, this);
    }
}
