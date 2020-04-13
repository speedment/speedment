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
import com.speedment.runtime.compute.internal.expression.CastUtil;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.MapperUtil;
import com.speedment.runtime.compute.trait.*;

import java.util.function.Function;

/**
 * Expression that given an entity returns a {@code short} value. This
 * expression can be implemented using a lambda, or it can be a result of
 * another operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 *
 * @see ToShortFunction
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToShort<T>
extends Expression<T>,
        ToShortFunction<T>,
        HasAsDouble<T>,
        HasAsInt<T>,
        HasAsLong<T>,
        HasAbs<ToShort<T>>,
        HasSign<ToByte<T>>,
        HasSqrt<ToDouble<T>>,
        HasNegate<ToShort<T>>,
        HasPow<T>,
        HasPlus<T>,
        HasMinus<T>,
        HasMultiply<T>,
        HasDivide<T>,
        HasMap<T, ShortUnaryOperator, ToShort<T>>,
        HasMapToDouble<T, ShortToDoubleFunction>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    
    /**
     * Returns a typed {@code ToShort<T>} using the provided {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToShort<T>} using the provided {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T> ToShort<T> of(ToShortFunction<T> lambda) {
        if (lambda instanceof ToShort) {
            return (ToShort<T>) lambda;
        } else {
            return lambda::applyAsShort;
        }
    }
    
    @Override
    short applyAsShort(T object);

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.SHORT;
    }

    @Override
    default ToDouble<T> asDouble() {
        return CastUtil.castShortToDouble(this);
    }

    @Override
    default ToInt<T> asInt() {
        return CastUtil.castShortToInt(this);
    }

    @Override
    default ToLong<T> asLong() {
        return CastUtil.castShortToLong(this);
    }

    @Override
    default ToDouble<T> mapToDouble(ShortToDoubleFunction operator) {
        return MapperUtil.mapShortToDouble(this, operator);
    }

    @Override
    default ToShort<T> map(ShortUnaryOperator operator) {
        return MapperUtil.mapShort(this, operator);
    }

    @Override
    default ToShort<T> abs() {
        return Expressions.abs(this);
    }

    @Override
    default ToByte<T> sign() {
        return Expressions.sign(this);
    }

    @Override
    default ToDouble<T> sqrt() {
        return Expressions.sqrt(this);
    }

    @Override
    default ToDouble<T> pow(int power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToDouble<T> pow(double power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToDouble<T> pow(ToInt<T> power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToDouble<T> pow(ToDouble<T> power) {
        return Expressions.pow(this, power);
    }

    @Override
    default ToInt<T> plus(byte other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToInt<T> plus(ToByte<T> other) {
        return Expressions.plus(this.asInt(), other);
    }

    @Override
    default ToInt<T> plus(int other) {
        return Expressions.plus(this.asInt(), other);
    }

    @Override
    default ToInt<T> plus(ToInt<T> other) {
        return Expressions.plus(this.asInt(), other);
    }

    @Override
    default ToLong<T> plus(long other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToLong<T> plus(ToLong<T> other) {
        return Expressions.plus(this.asLong(), other);
    }

    @Override
    default ToDouble<T> plus(double other) {
        return Expressions.plus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> plus(ToDouble<T> other) {
        return Expressions.plus(this.asDouble(), other);
    }

    @Override
    default ToInt<T> minus(byte other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToInt<T> minus(ToByte<T> other) {
        return Expressions.minus(this.asInt(), other);
    }

    @Override
    default ToInt<T> minus(int other) {
        return Expressions.minus(this.asInt(), other);
    }

    @Override
    default ToInt<T> minus(ToInt<T> other) {
        return Expressions.minus(this.asInt(), other);
    }

    @Override
    default ToLong<T> minus(long other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToLong<T> minus(ToLong<T> other) {
        return Expressions.minus(this.asLong(), other);
    }

    @Override
    default ToDouble<T> minus(double other) {
        return Expressions.minus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> minus(ToDouble<T> other) {
        return Expressions.minus(this.asDouble(), other);
    }

    @Override
    default ToInt<T> multiply(byte other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToInt<T> multiply(ToByte<T> other) {
        return Expressions.multiply(this.asInt(), other);
    }

    @Override
    default ToInt<T> multiply(int other) {
        return Expressions.multiply(this.asInt(), other);
    }

    @Override
    default ToInt<T> multiply(ToInt<T> other) {
        return Expressions.multiply(this.asInt(), other);
    }

    @Override
    default ToLong<T> multiply(long other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToLong<T> multiply(ToLong<T> other) {
        return Expressions.multiply(this.asLong(), other);
    }

    @Override
    default ToDouble<T> multiply(double other) {
        return Expressions.multiply(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> multiply(ToDouble<T> other) {
        return Expressions.multiply(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> divide(int divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToInt<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(long divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToLong<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(double divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToDouble<T> divide(ToDouble<T> divisor) {
        return Expressions.divide(this, divisor);
    }

    @Override
    default ToShort<T> negate() {
        return Expressions.negate(this);
    }

    @Override
    default long hash(T object) {
        return applyAsShort(object);
    }

    @Override
    default int compare(T first, T second) {
        return Short.compare(
            applyAsShort(first),
            applyAsShort(second)
        );
    }

    @Override
    default <V> ToShortNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToShort(casted, this);
    }
}
