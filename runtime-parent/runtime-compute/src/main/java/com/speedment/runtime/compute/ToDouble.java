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
import com.speedment.runtime.compute.internal.ConstantDoubleImpl;
import com.speedment.runtime.compute.internal.expression.CastUtil;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.MapperUtil;
import com.speedment.runtime.compute.trait.*;

import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

/**
 * Expression that given an entity returns a {@code double} value. This
 * expression can be implemented using a lambda, or it can be a result of
 * another operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 *
 * @see ToDoubleFunction
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToDouble<T>
extends Expression<T>,
        ToDoubleFunction<T>,
        HasAsDouble<T>,
        HasAsInt<T>,
        HasAsLong<T>,
        HasAbs<ToDouble<T>>,
        HasSign<ToByte<T>>,
        HasSqrt<ToDouble<T>>,
        HasNegate<ToDouble<T>>,
        HasPow<T>,
        HasPlus<T>,
        HasMinus<T>,
        HasMultiply<T>,
        HasDivide<T>,
        HasMap<T, DoubleUnaryOperator, ToDouble<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    /**
     * Returns a typed {@code ToDouble<T>} using the provided {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToDouble<T>} using the provided {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T> ToDouble<T> of(ToDoubleFunction<T> lambda) {
        if (lambda instanceof ToDouble) {
            return (ToDouble<T>) lambda;
        } else {
            return lambda::applyAsDouble;
        }
    }
    
    /**
     * Returns an implementation of this interface that regardless of input,
     * always returns the value specified.
     *
     * @param value the value to always return
     * @param <T> the type of the ignored input
     * @return the constant expression
     */
    static <T> ToDouble<T> constant(double value) {
        return new ConstantDoubleImpl<>(value);
    }

    @Override
    double applyAsDouble(T object);

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.DOUBLE;
    }

    @Override
    default ToDouble<T> asDouble() {
        return this;
    }

    @Override
    default ToInt<T> asInt() {
        return CastUtil.castDoubleToInt(this);
    }

    @Override
    default ToLong<T> asLong() {
        return CastUtil.castDoubleToLong(this);
    }

    @Override
    default ToDouble<T> map(DoubleUnaryOperator operator) {
        return MapperUtil.mapDouble(this, operator);
    }

    @Override
    default ToDouble<T> abs() {
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
    default ToDouble<T> negate() {
        return Expressions.negate(this);
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
    default ToDouble<T> plus(byte other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToDouble<T> plus(ToByte<T> other) {
        return Expressions.plus(this, other.asDouble());
    }

    @Override
    default ToDouble<T> plus(int other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToDouble<T> plus(ToInt<T> other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToDouble<T> plus(long other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToDouble<T> plus(ToLong<T> other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToDouble<T> plus(double other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToDouble<T> plus(ToDouble<T> other) {
        return Expressions.plus(this, other);
    }

    @Override
    default ToDouble<T> minus(byte other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToDouble<T> minus(ToByte<T> other) {
        return Expressions.minus(this, other.asDouble());
    }

    @Override
    default ToDouble<T> minus(int other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToDouble<T> minus(ToInt<T> other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToDouble<T> minus(long other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToDouble<T> minus(ToLong<T> other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToDouble<T> minus(double other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToDouble<T> minus(ToDouble<T> other) {
        return Expressions.minus(this, other);
    }

    @Override
    default ToDouble<T> multiply(byte other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToDouble<T> multiply(ToByte<T> other) {
        return Expressions.multiply(this, other.asDouble());
    }

    @Override
    default ToDouble<T> multiply(int other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToDouble<T> multiply(ToInt<T> other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToDouble<T> multiply(long other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToDouble<T> multiply(ToLong<T> other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToDouble<T> multiply(double other) {
        return Expressions.multiply(this, other);
    }

    @Override
    default ToDouble<T> multiply(ToDouble<T> other) {
        return Expressions.multiply(this, other);
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
    default long hash(T object) {
        final long l = Double.doubleToLongBits(applyAsDouble(object));
        return (int) (l ^ (l >>> 32));
    }

    @Override
    default int compare(T first, T second) {
        return Double.compare(
            applyAsDouble(first),
            applyAsDouble(second)
        );
    }

    @Override
    default <V> ToDoubleNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToDouble(casted, this);
    }
}
