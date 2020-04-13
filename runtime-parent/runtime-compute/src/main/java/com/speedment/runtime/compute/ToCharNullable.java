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

import com.speedment.common.function.ToCharFunction;
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
 * Expression that given an entity returns a {@code char} value, or
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
public interface ToCharNullable<T>
extends Expression<T>,
        ToCharFunction<T>,
        ToNullable<T, Character, ToChar<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    /**
     * Returns a typed {@code ToCharNullable<T>} using the provided
     * {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToCharNullable<T>} using the provided
     * {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T> ToCharNullable<T> of(Function<T, Character> lambda) {
        if (lambda instanceof ToCharNullable) {
            return (ToCharNullable<T>) lambda;
        } else {
            return lambda::apply;
        }
    }
    
    @Override
    default ExpressionType expressionType() {
        return ExpressionType.CHAR_NULLABLE;
    }

    @Override
    default char applyAsChar(T object) {
        return apply(object);
    }

    @Override
    default ToChar<T> orThrow() {
        return OrElseThrowUtil.charOrElseThrow(this);
    }

    @Override
    default ToChar<T> orElseGet(ToChar<T> getter) {
        return OrElseGetUtil.charOrElseGet(this, getter);
    }

    @Override
    default ToChar<T> orElse(Character value) {
        return OrElseUtil.charOrElse(this, value);
    }

    @Override
    default long hash(T object) {
        return isNull(object) ? 0 : applyAsChar(object);
    }

    @Override
    default int compare(T first, T second) {
        if (isNull(first)) {
            return isNull(second) ? 0 : 1;
        } else if (isNull(second)) {
            return -1;
        } else {
            return Character.compare(
                applyAsChar(first),
                applyAsChar(second)
            );
        }
    }

    @Override
    default <V> ToCharNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToCharNullable(casted, this);
    }
}
