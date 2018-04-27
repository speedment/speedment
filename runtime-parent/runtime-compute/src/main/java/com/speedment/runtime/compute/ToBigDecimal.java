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
import java.util.function.UnaryOperator;

/**
 * Expression that given an entity returns a non-null {@code String} value. This
 * expression can be implemented using a lambda, or it can be a result of
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
        //HasPow<T>,
        //HasPlus<T, ToShort<T>, ToInt<T>, ToLong<T>>,
        //HasMinus<T, ToShort<T>, ToInt<T>, ToLong<T>>,
        //HasMultiply<T, ToInt<T>, ToInt<T>, ToLong<T>>,
        //HasDivide<T>,
        HasMap<T, UnaryOperator<BigDecimal>, ToBigDecimal<T>>,
        HasHash<T>,
        HasCompare<T> {

    @Override
    BigDecimal apply(T object);

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.BIG_DECIMAL;
    }

    @Override
    default ToDouble<T> asDouble() {
        return CastUtil.castToDouble(this);
    }

    @Override
    default ToInt<T> asInt() {
        return CastUtil.castToInt(this);
    }

    @Override
    default ToLong<T> asLong() {
        return CastUtil.castToLong(this);
    }

    @Override
    default ToBigDecimal<T> abs() {
        return AbsUtil.abs(this);
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
    default ToBigDecimal<T> negate() {
        return Expressions.negate(this);
    }

    @Override
    default ToBigDecimal<T> map(UnaryOperator<BigDecimal> mapper) {
        return MapperUtil.map(this, mapper);
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
    @SuppressWarnings("unchecked")
    default <V> ToBigDecimal<V> compose(Function<? super V, ? extends T> before) {
        return ComposedUtil.compose((Function<V, T>) before, this);
    }

}
