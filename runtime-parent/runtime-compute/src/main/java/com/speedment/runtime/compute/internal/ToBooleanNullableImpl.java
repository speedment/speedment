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

import com.speedment.common.function.BooleanToDoubleFunction;
import com.speedment.common.function.BooleanUnaryOperator;
import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToBooleanNullable;
import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.expression.NullableExpression;

import java.util.Objects;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ToBooleanNullableImpl<T>
implements NullableExpression<T, ToBoolean<T>>, ToBooleanNullable<T> {

    private final ToBoolean<T> original;
    private final Predicate<T> isNull;

    public ToBooleanNullableImpl(ToBoolean<T> original, Predicate<T> isNull) {
        this.original = requireNonNull(original);
        this.isNull   = requireNonNull(isNull);
    }

    @Override
    public ToBoolean<T> inner() {
        return original;
    }

    @Override
    public Predicate<T> isNullPredicate() {
        return isNull;
    }

    @Override
    public Boolean apply(T t) {
        return isNull.test(t) ? null : original.applyAsBoolean(t);
    }

    @Override
    public boolean applyAsBoolean(T t) {
        return original.applyAsBoolean(t);
    }

    @Override
    public ToBoolean<T> orThrow() {
        return original;
    }

    @Override
    public ToBoolean<T> orElseGet(ToBoolean<T> getter) {
        return t -> isNull.test(t)
            ? getter.applyAsBoolean(t)
            : original.applyAsBoolean(t);
    }

    @Override
    public ToBoolean<T> orElse(Boolean value) {
        return t -> isNull.test(t) ? value : original.applyAsBoolean(t);
    }

    @Override
    public ToDoubleNullable<T> mapToDoubleIfPresent(BooleanToDoubleFunction mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsDouble(original.applyAsBoolean(t));
    }

    @Override
    public ToBooleanNullable<T> mapIfPresent(BooleanUnaryOperator mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsBoolean(original.applyAsBoolean(t));
    }

    @Override
    public long hash(T object) {
        if (isNull.test(object)) {
            return 2;
        } else {
            return original.applyAsBoolean(object) ? 1 : 0;
        }
    }

    @Override
    public int compare(T first, T second) {
        final boolean f = isNull(first);
        final boolean s = isNull(second);
        if (f && s) return 0;
        else if (f) return 1;
        else if (s) return -1;
        else return Boolean.compare(
            original.applyAsBoolean(first),
            original.applyAsBoolean(second)
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