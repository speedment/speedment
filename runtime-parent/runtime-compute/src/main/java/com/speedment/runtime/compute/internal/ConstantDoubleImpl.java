package com.speedment.runtime.compute.internal;

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.expression.ConstantExpression;

/**
 * Default implementation of {@link ConstantExpression} used for {@code double}
 * values.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ConstantDoubleImpl<T>
implements ToDouble<T>, ConstantExpression<T, Double> {

    private final double constant;

    public ConstantDoubleImpl(double constant) {
        this.constant = constant;
    }

    @Override
    public double applyAsDouble(T object) {
        return constant;
    }

    @Override
    public Double getValue() {
        return constant;
    }
}
