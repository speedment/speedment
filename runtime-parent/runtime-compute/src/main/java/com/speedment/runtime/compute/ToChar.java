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

import com.speedment.common.function.CharUnaryOperator;
import com.speedment.common.function.ToCharFunction;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.CastUtil;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.MapperUtil;
import com.speedment.runtime.compute.trait.HasAsDouble;
import com.speedment.runtime.compute.trait.HasAsInt;
import com.speedment.runtime.compute.trait.HasAsLong;
import com.speedment.runtime.compute.trait.HasCase;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasCompose;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasMap;

import java.util.function.Function;

/**
 * Expression that given an entity returns a {@code char} value. This expression
 * can be implemented using a lambda, or it can be a result of another
 * operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 *
 * @see ToCharFunction
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToChar<T>
extends Expression<T>,
        ToCharFunction<T>,
        HasAsDouble<T>,
        HasAsInt<T>,
        HasAsLong<T>,
        HasMap<T, CharUnaryOperator, ToChar<T>>,
        HasCase<T, ToChar<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {
    
    /**
     * Returns a typed {@code ToChar<T>} using the provided {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToChar<T>} using the provided {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T> ToChar<T> of(ToCharFunction<T> lambda) {
        if (lambda instanceof ToChar) {
            return (ToChar<T>) lambda;
        } else {
            return lambda::applyAsChar;
        }
    }

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.CHAR;
    }

    @Override
    default ToDouble<T> asDouble() {
        return CastUtil.castCharToDouble(this);
    }

    @Override
    default ToInt<T> asInt() {
        return CastUtil.castCharToInt(this);
    }

    @Override
    default ToLong<T> asLong() {
        return CastUtil.castCharToLong(this);
    }

    @Override
    default ToChar<T> map(CharUnaryOperator operator) {
        return MapperUtil.mapChar(this, operator);
    }

    @Override
    default ToChar<T> toUpperCase() {
        return map(Character::toUpperCase);
    }

    @Override
    default ToChar<T> toLowerCase() {
        return map(Character::toLowerCase);
    }

    @Override
    default long hash(T object) {
        return applyAsChar(object);
    }

    @Override
    default int compare(T first, T second) {
        final char f = applyAsChar(first);
        final char s = applyAsChar(second);
        return Character.compare(f, s);
    }

    @Override
    default <V> ToCharNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToChar(casted, this);
    }
}
