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
import com.speedment.runtime.compute.internal.expression.AbsUtil;
import com.speedment.runtime.compute.internal.expression.CastUtil;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.MapperUtil;
import com.speedment.runtime.compute.trait.*;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

/**
 * Expression that given an entity returns a non-null {@code BigDecimal} value.
 * This expression can be implemented using a lambda, or it can be a result of
 * another operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 *
 * @see Function
 *
 * @author Per Minborg
 * @since  3.1.0
 */
@FunctionalInterface
public interface ToBigDecimal<T>
extends Expression<T>,
        Function<T, BigDecimal>,
        HasAsInt<T>,
        HasAsLong<T>,
        HasAsDouble<T>,
        HasAbs<ToBigDecimal<T>>,
        HasSign<ToByte<T>>,
        HasSqrt<ToDouble<T>>,
        HasNegate<ToBigDecimal<T>>,
        HasPow<T>,
        HasPlus<T>,
        HasMinus<T>,
        HasMultiply<T>,
        HasDivide<T>,
        HasMap<T, UnaryOperator<BigDecimal>, ToBigDecimal<T>>,
        HasMapToDouble<T, ToDoubleFunction<BigDecimal>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    /**
     * Returns a typed {@code ToBigDecimal<T>} using the provided
     * {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToBigDecimal<T>} using the provided {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T> ToBigDecimal<T> of(Function<T, BigDecimal> lambda) {
        if (lambda instanceof ToBigDecimal) {
            return (ToBigDecimal<T>) lambda;
        } else {
            return lambda::apply;
        }
    }
    
    @Override
    BigDecimal apply(T object);

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.BIG_DECIMAL;
    }

    @Override
    default ToDouble<T> asDouble() {
        return CastUtil.castBigDecimalToDouble(this);
    }

    @Override
    default ToInt<T> asInt() {
        return CastUtil.castBigDecimalToInt(this);
    }

    @Override
    default ToLong<T> asLong() {
        return CastUtil.castBigDecimalToLong(this);
    }

    @Override
    default ToBigDecimal<T> abs() {
        return AbsUtil.absBigDecimal(this);
    }

    @Override
    default ToByte<T> sign() {
        return Expressions.sign(this);
    }

    @Override
    default ToDouble<T> sqrt() {
        return this.asDouble().sqrt();
    }

    @Override
    default ToBigDecimal<T> negate() {
        return Expressions.negate(this);
    }

    @Override
    default ToDouble<T> pow(int power) {
        return Expressions.pow(this.asDouble(), power);
    }

    @Override
    default ToDouble<T> pow(double power) {
        return Expressions.pow(this.asDouble(), power);
    }

    @Override
    default ToDouble<T> pow(ToInt<T> power) {
        return Expressions.pow(this.asDouble(), power);
    }

    @Override
    default ToDouble<T> pow(ToDouble<T> power) {
        return Expressions.pow(this.asDouble(), power);
    }

    @Override
    default ToDouble<T> plus(byte other) {
        return Expressions.plus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> plus(ToByte<T> other) {
        return Expressions.plus(this.asDouble(), other.asInt());
    }

    @Override
    default ToDouble<T> plus(int other) {
        return Expressions.plus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> plus(ToInt<T> other) {
        return Expressions.plus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> plus(long other) {
        return Expressions.plus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> plus(ToLong<T> other) {
        return Expressions.plus(this.asDouble(), other);
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
    default ToDouble<T> minus(byte other) {
        return Expressions.minus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> minus(ToByte<T> other) {
        return Expressions.minus(this.asDouble(), other.asInt());
    }

    @Override
    default ToDouble<T> minus(int other) {
        return Expressions.minus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> minus(ToInt<T> other) {
        return Expressions.minus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> minus(long other) {
        return Expressions.minus(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> minus(ToLong<T> other) {
        return Expressions.minus(this.asDouble(), other);
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
    default ToDouble<T> multiply(byte other) {
        return Expressions.multiply(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> multiply(ToByte<T> other) {
        return Expressions.multiply(this.asDouble(), other.asInt());
    }

    @Override
    default ToDouble<T> multiply(int other) {
        return Expressions.multiply(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> multiply(ToInt<T> other) {
        return Expressions.multiply(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> multiply(long other) {
        return Expressions.multiply(this.asDouble(), other);
    }

    @Override
    default ToDouble<T> multiply(ToLong<T> other) {
        return Expressions.multiply(this.asDouble(), other);
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
        return Expressions.divide(this.asDouble(), divisor);
    }

    @Override
    default ToDouble<T> divide(ToInt<T> divisor) {
        return Expressions.divide(this.asDouble(), divisor);
    }

    @Override
    default ToDouble<T> divide(long divisor) {
        return Expressions.divide(this.asDouble(), divisor);
    }

    @Override
    default ToDouble<T> divide(ToLong<T> divisor) {
        return Expressions.divide(this.asDouble(), divisor);
    }

    @Override
    default ToDouble<T> divide(double divisor) {
        return Expressions.divide(this.asDouble(), divisor);
    }

    @Override
    default ToDouble<T> divide(ToDouble<T> divisor) {
        return Expressions.divide(this.asDouble(), divisor);
    }

    @Override
    default ToBigDecimal<T> map(UnaryOperator<BigDecimal> mapper) {
        return MapperUtil.mapBigDecimal(this, mapper);
    }

    @Override
    default ToDouble<T> mapToDouble(ToDoubleFunction<BigDecimal> mapper) {
        return MapperUtil.mapBigDecimalToDouble(this, mapper);
    }

    @Override
    default long hash(T object) {
        return apply(object).hashCode();
    }

    @Override
    default int compare(T first, T second) {
        return apply(first).compareTo(apply(second));
    }

    @Override
    default <V> ToBigDecimalNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToBigDecimal(casted, this);
    }
}
