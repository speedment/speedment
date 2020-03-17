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

import com.speedment.common.function.BooleanToDoubleFunction;
import com.speedment.common.function.BooleanUnaryOperator;
import com.speedment.common.function.ToBooleanFunction;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.CastUtil;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.MapperUtil;
import com.speedment.runtime.compute.trait.HasAsDouble;
import com.speedment.runtime.compute.trait.HasAsInt;
import com.speedment.runtime.compute.trait.HasAsLong;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasCompose;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasMap;
import com.speedment.runtime.compute.trait.HasMapToDouble;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Expression that given an entity returns a {@code boolean} value. This
 * expression can be implemented using a lambda, or it can be a result of
 * another operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 *
 * @see ToBooleanFunction
 * @see Predicate
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToBoolean<T>
extends Expression<T>,
        ToBooleanFunction<T>,
        HasAsDouble<T>,
        HasAsInt<T>,
        HasAsLong<T>,
        HasMap<T, BooleanUnaryOperator, ToBoolean<T>>,
        HasMapToDouble<T, BooleanToDoubleFunction>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    /**
     * Returns a typed {@code ToBoolean<T>} using the provided {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToBoolean<T>} using the provided {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T> ToBoolean<T> of(Predicate<T> lambda) {
        if (lambda instanceof ToBoolean) {
            return (ToBoolean<T>) lambda;
        } else {
            return lambda::test;
        }
    }

    @Override
    boolean applyAsBoolean(T object);

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.BOOLEAN;
    }

    @Override
    default ToDouble<T> asDouble() {
        return CastUtil.castBooleanToDouble(this);
    }

    @Override
    default ToInt<T> asInt() {
        return CastUtil.castBooleanToInt(this);
    }

    @Override
    default ToLong<T> asLong() {
        return CastUtil.castBooleanToLong(this);
    }

    @Override
    default ToDouble<T> mapToDouble(BooleanToDoubleFunction operator) {
        return MapperUtil.mapBooleanToDouble(this, operator);
    }

    @Override
    default ToBoolean<T> map(BooleanUnaryOperator operator) {
        return MapperUtil.mapBoolean(this, operator);
    }

    @Override
    default long hash(T object) {
        return applyAsBoolean(object) ? 1 : 0;
    }

    @Override
    default int compare(T first, T second) {
        final boolean f = applyAsBoolean(first);
        final boolean s = applyAsBoolean(second);
        return Boolean.compare(f, s);
    }

    /**
     * {@inheritDoc}
     * <p>
     * {@code ToBoolean} is a bit special when it comes to the
     * {@code compose()}-method. If the {@code before} method returns
     * {@code null}, the composed expression will return {@code false}. This is
     * to remain compatible with how Speedment handles predicates in streams.
     * To get a {@code ToBooleanNullable} that acts as you might expect, instead
     * use {@link #compose}
     *
     * @param before the function to apply before this function is applied
     * @param <V>  the input type of the {@code before} function
     * @return  the composed expression
     */
    @Override
    default <V> ToBoolean<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToBoolean(casted, this);
    }

    /**
     * Returns a composed expression that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param before the function to apply before this function is applied
     * @param <V>  the input type of the {@code before} function
     * @return  the composed expression
     *
     * @since 3.1.2
     */
    default <V> ToBooleanNullable<V> composeNullable(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToBooleanAsNullable(casted, this);
    }
}
