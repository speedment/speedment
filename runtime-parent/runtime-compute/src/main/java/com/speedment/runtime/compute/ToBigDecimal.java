package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.MapperUtil;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasMap;

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
