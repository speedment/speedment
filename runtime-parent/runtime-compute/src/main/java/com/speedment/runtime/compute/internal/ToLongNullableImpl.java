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

import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToLongNullable;
import com.speedment.runtime.compute.expression.NullableExpression;

import java.util.Objects;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ToLongNullableImpl<T>
    implements NullableExpression<T, ToLong<T>>, ToLongNullable<T> {

    private final ToLong<T> original;
    private final Predicate<T> isNull;

    public ToLongNullableImpl(ToLong<T> original, Predicate<T> isNull) {
        this.original = requireNonNull(original);
        this.isNull   = requireNonNull(isNull);
    }

    @Override
    public ToLong<T> inner() {
        return original;
    }

    @Override
    public Predicate<T> isNullPredicate() {
        return isNull;
    }

    @Override
    public Long apply(T t) {
        return isNull.test(t) ? null : original.applyAsLong(t);
    }

    @Override
    public long applyAsLong(T t) {
        return original.applyAsLong(t);
    }

    @Override
    public ToLong<T> orThrow() {
        return original;
    }

    @Override
    public ToLong<T> orElseGet(ToLong<T> getter) {
        return t -> isNull.test(t)
            ? getter.applyAsLong(t)
            : original.applyAsLong(t);
    }

    @Override
    public ToLong<T> orElse(Long value) {
        return t -> isNull.test(t) ? value : original.applyAsLong(t);
    }

    @Override
    public ToDoubleNullable<T> mapToDoubleIfPresent(LongToDoubleFunction mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsDouble(original.applyAsLong(t));
    }

    @Override
    public ToLongNullable<T> mapIfPresent(LongUnaryOperator mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsLong(original.applyAsLong(t));
    }

    @Override
    public long hash(T object) {
        return isNull.test(object)
            ? 0xffffffffffffffffL
            : original.applyAsLong(object);
    }

    @Override
    public int compare(T first, T second) {
        final boolean f = isNull(first);
        final boolean s = isNull(second);
        if (f && s) return 0;
        else if (f) return 1;
        else if (s) return -1;
        else return Long.compare(
            original.applyAsLong(first),
            original.applyAsLong(second)
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