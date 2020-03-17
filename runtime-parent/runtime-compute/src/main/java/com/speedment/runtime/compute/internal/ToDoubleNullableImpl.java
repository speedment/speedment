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
package com.speedment.runtime.compute.internal;

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.expression.NullableExpression;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ToDoubleNullableImpl<T>
    implements NullableExpression<T, ToDouble<T>>, ToDoubleNullable<T> {

    private final ToDouble<T> original;
    private final Predicate<T> isNull;

    public ToDoubleNullableImpl(ToDouble<T> original, Predicate<T> isNull) {
        this.original = requireNonNull(original);
        this.isNull   = requireNonNull(isNull);
    }

    @Override
    public ToDouble<T> inner() {
        return original;
    }

    @Override
    public Predicate<T> isNullPredicate() {
        return isNull;
    }

    @Override
    public Double apply(T t) {
        return isNull.test(t) ? null : original.applyAsDouble(t);
    }

    @Override
    public double applyAsDouble(T t) {
        return original.applyAsDouble(t);
    }

    @Override
    public ToDouble<T> orThrow() {
        return original;
    }

    @Override
    public ToDouble<T> orElseGet(ToDouble<T> getter) {
        return t -> isNull.test(t)
            ? getter.applyAsDouble(t)
            : original.applyAsDouble(t);
    }

    @Override
    public ToDouble<T> orElse(Double value) {
        return t -> isNull.test(t) ? value : original.applyAsDouble(t);
    }

    @Override
    public ToDoubleNullable<T> mapIfPresent(DoubleUnaryOperator mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsDouble(original.applyAsDouble(t));
    }

    @Override
    public long hash(T object) {
        return Double.doubleToLongBits(
            isNull.test(object)
                ? Double.NEGATIVE_INFINITY // Unlikely value
                : Double.doubleToLongBits(original.applyAsDouble(object))
        );
    }

    @Override
    public int compare(T first, T second) {
        final boolean f = isNull(first);
        final boolean s = isNull(second);
        if (f && s) return 0;
        else if (f) return 1;
        else if (s) return -1;
        else return Double.compare(
            original.applyAsDouble(first),
            original.applyAsDouble(second)
        );
    }

    @Override
    public boolean isNull(T object) {
        return isNull.test(object);
    }

    @Override
    public boolean isNotNull(T object) {
        return !isNull.test(object);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof NullableExpression)) return false;
        final NullableExpression<?, ?> that = (NullableExpression<?, ?>) o;
        return Objects.equals(original, that.inner()) &&
            Objects.equals(isNull, that.isNullPredicate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(original, isNull);
    }
}