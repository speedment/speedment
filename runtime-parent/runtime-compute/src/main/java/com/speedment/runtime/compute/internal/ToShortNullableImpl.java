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

import com.speedment.common.function.ShortToDoubleFunction;
import com.speedment.common.function.ShortUnaryOperator;
import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.ToShortNullable;
import com.speedment.runtime.compute.expression.NullableExpression;

import java.util.Objects;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ToShortNullableImpl<T>
implements NullableExpression<T, ToShort<T>>, ToShortNullable<T> {

    private final ToShort<T> original;
    private final Predicate<T> isNull;

    public ToShortNullableImpl(ToShort<T> original, Predicate<T> isNull) {
        this.original = requireNonNull(original);
        this.isNull   = requireNonNull(isNull);
    }

    @Override
    public ToShort<T> inner() {
        return original;
    }

    @Override
    public Predicate<T> isNullPredicate() {
        return isNull;
    }

    @Override
    public Short apply(T t) {
        return isNull.test(t) ? null : original.applyAsShort(t);
    }

    @Override
    public short applyAsShort(T t) {
        return original.applyAsShort(t);
    }

    @Override
    public ToShort<T> orThrow() {
        return original;
    }

    @Override
    public ToShort<T> orElseGet(ToShort<T> getter) {
        return t -> isNull.test(t)
            ? getter.applyAsShort(t)
            : original.applyAsShort(t);
    }

    @Override
    public ToShort<T> orElse(Short value) {
        return t -> isNull.test(t) ? value : original.applyAsShort(t);
    }

    @Override
    public ToDoubleNullable<T> mapToDoubleIfPresent(ShortToDoubleFunction mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsDouble(original.applyAsShort(t));
    }

    @Override
    public ToShortNullable<T> mapIfPresent(ShortUnaryOperator mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsShort(original.applyAsShort(t));
    }

    @Override
    public long hash(T object) {
        return isNull.test(object)
            ? (0xffff + 1) // Will never occur naturally
            : original.applyAsShort(object);
    }

    @Override
    public int compare(T first, T second) {
        final boolean f = isNull(first);
        final boolean s = isNull(second);
        if (f && s) return 0;
        else if (f) return 1;
        else if (s) return -1;
        else return Short.compare(
            original.applyAsShort(first),
            original.applyAsShort(second)
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