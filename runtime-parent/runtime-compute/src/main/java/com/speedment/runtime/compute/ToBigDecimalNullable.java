package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.OrElseGetUtil;
import com.speedment.runtime.compute.internal.expression.OrElseThrowUtil;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.ToNullable;

import java.math.BigDecimal;
import java.util.function.Function;

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
public interface ToBigDecimalNullable<T>
extends Expression<T>,
        ToNullable<T, BigDecimal>,
        HasHash<T>,
        HasCompare<T> {

    @Override
    BigDecimal apply(T object);

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.BIG_DECIMAL_NULLABLE;
    }

    default ToBigDecimal<T> orThrow() throws NullPointerException {
        return OrElseThrowUtil.orElseThrow(this);
    }

    default ToBigDecimal<T> orElseGet(ToBigDecimal<T> getter) {
        return OrElseGetUtil.orElseGet(this, getter);
    }

    default ToBigDecimal<T> orElse(BigDecimal value) {
        return OrElseUtil.orElse(this, value);
    }

    @Override
    default long hash(T object) {
        return isNull(object) ? 0 : apply(object).hashCode();
    }

    @Override
    default int compare(T first, T second) {
        if (isNull(first)) {
            return isNull(second) ? 0 : 1;
        } else if (isNull(second)) {
            return -1;
        } else {
            return apply(first).compareTo(apply(second));
        }
    }
}
