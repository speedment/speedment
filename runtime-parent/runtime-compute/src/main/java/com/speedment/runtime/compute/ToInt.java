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
import com.speedment.runtime.compute.internal.expression.CastUtil;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.MapperUtil;
import com.speedment.runtime.compute.trait.*;

import java.util.function.Function;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;

/**
 * Expression that given an entity returns an {@code int} value. This expression
 * can be implemented using a lambda, or it can be a result of another
 * operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 *
 * @see ToIntFunction
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToInt<T>
extends Expression<T>,
        ToIntFunction<T>,
        HasAsDouble<T>,
        HasAsInt<T>,
        HasAsLong<T>,
        HasAbs<ToInt<T>>,
        HasSign<ToByte<T>>,
        HasSqrt<ToDouble<T>>,
        HasNegate<ToInt<T>>,
        HasPow<T>,
        HasPlus<T>,
        HasMinus<T>,
        HasMultiply<T>,
        HasDivide<T>,
        HasMap<T, IntUnaryOperator, ToInt<T>>,
        HasMapToDouble<T, IntToDoubleFunction>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    
    /**
     * Returns a typed {@code ToInt<T>} using the provided {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToInt<T>} using the provided {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T> ToInt<T> of(ToIntFunction<T> lambda) {
        if (lambda instanceof ToInt) {
            return (ToInt<T>) lambda;
        } else {
            return lambda::applyAsInt;
        }
    }
    
    @Override
    int applyAsInt(T object);

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.INT;
    }

    @Override
    default ToDouble<T> asDouble() {
        return CastUtil.castIntToDouble(this);
    }

    @Override
    default ToInt<T> asInt() {
        return this;
    }

    @Override
    default ToLong<T> asLong() {
        return CastUtil.castIntToLong(this);
    }

    @Override
    default ToDouble<T> mapToDouble(IntToDoubleFunction operator) {
        return MapperUtil.mapIntToDouble(this, operator);
    }

    @Override
    default ToInt<T> map(IntUnaryOperator operator) {
        return MapperUtil.mapInt(this, operator);
    }

    @Override
    default ToInt<T> abs() {
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
        return Expressions.plus(this, other);
    }

    @Override
    default ToInt<T> plus(int other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToInt<T> plus(ToInt<T> other) {
        return Expressions.plus(this, other);
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
        return Expressions.minus(this, other);
    }

    @Override
    default ToInt<T> minus(int other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToInt<T> minus(ToInt<T> other) {
        return Expressions.minus(this, other);
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
        return Expressions.multiply(this, other.asInt());
    }

    @Override
    default ToInt<T> multiply(int other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToInt<T> multiply(ToInt<T> other) {
        return Expressions.multiply(this, other);
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
    default ToInt<T> negate() {
        return Expressions.negate(this);
    }

    @Override
    default long hash(T object) {
        return applyAsInt(object);
    }

    @Override
    default int compare(T first, T second) {
        return Integer.compare(
            applyAsInt(first),
            applyAsInt(second)
        );
    }

    @Override
    default <V> ToIntNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToInt(casted, this);
    }
}
