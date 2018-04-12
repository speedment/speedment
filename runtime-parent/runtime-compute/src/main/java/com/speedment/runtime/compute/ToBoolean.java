package com.speedment.runtime.compute;

import com.speedment.common.function.BooleanToDoubleFunction;
import com.speedment.common.function.BooleanUnaryOperator;
import com.speedment.common.function.ToBooleanFunction;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.CastUtil;
import com.speedment.runtime.compute.trait.HasAsDouble;
import com.speedment.runtime.compute.trait.HasAsInt;
import com.speedment.runtime.compute.trait.HasAsLong;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;

/**
 * Expression that given an entity returns a {@code boolean} value. This
 * expression can be implemented using a lamda, or it can be a result of another
 * operation. It has additional methods for operating on it.
 *
 * @see ToBooleanFunction
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToBoolean<T>
extends Expression,
        ToBooleanFunction<T>,
        HasAsDouble<T>,
        HasAsInt<T>,
        HasAsLong<T>,
        HasHash<T>,
        HasCompare<T> {

    boolean applyAsBoolean(T object);

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.BOOLEAN;
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

    default ToDouble<T> mapToDouble(BooleanToDoubleFunction operator) {
        return object -> operator.applyAsDouble(applyAsBoolean(object));
    }

    default ToBoolean<T> map(BooleanUnaryOperator operator) {
        return object -> operator.applyAsBoolean(applyAsBoolean(object));
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
}
