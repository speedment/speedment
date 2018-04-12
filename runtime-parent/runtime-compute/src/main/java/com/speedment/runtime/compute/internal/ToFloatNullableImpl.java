package com.speedment.runtime.compute.internal;

import com.speedment.common.function.FloatToDoubleFunction;
import com.speedment.common.function.FloatUnaryOperator;
import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToFloatNullable;
import com.speedment.runtime.compute.expression.NullableExpression;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ToFloatNullableImpl<T>
implements NullableExpression<T, ToFloat<T>>, ToFloatNullable<T> {

    private final ToFloat<T> original;
    private final Predicate<T> isNull;

    public ToFloatNullableImpl(ToFloat<T> original, Predicate<T> isNull) {
        this.original = requireNonNull(original);
        this.isNull   = requireNonNull(isNull);
    }

    @Override
    public ToFloat<T> getInner() {
        return original;
    }

    @Override
    public Predicate<T> getIsNull() {
        return isNull;
    }

    @Override
    public Float apply(T t) {
        return isNull.test(t) ? null : original.applyAsFloat(t);
    }

    @Override
    public float applyAsFloat(T t) throws NullPointerException {
        return original.applyAsFloat(t);
    }

    @Override
    public ToFloat<T> orThrow() throws NullPointerException {
        return original;
    }

    @Override
    public ToFloat<T> orElseGet(ToFloat<T> getter) {
        return t -> isNull.test(t)
            ? getter.applyAsFloat(t)
            : original.applyAsFloat(t);
    }

    @Override
    public ToFloat<T> orElse(float value) {
        return t -> isNull.test(t) ? value : original.applyAsFloat(t);
    }

    @Override
    public ToDoubleNullable<T> mapToDoubleIfPresent(FloatToDoubleFunction mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsDouble(original.applyAsFloat(t));
    }

    @Override
    public ToFloatNullable<T> mapIfPresent(FloatUnaryOperator mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsFloat(original.applyAsFloat(t));
    }

    @Override
    public long hash(T object) {
        return isNull.test(object)
            ? (0xffffffffL + 1) // Will never occur naturally
            : Float.floatToIntBits(original.applyAsFloat(object));
    }

    @Override
    public int compare(T first, T second) {
        final boolean f = isNull(first);
        final boolean s = isNull(second);
        if (f && s) return 0;
        else if (f) return 1;
        else if (s) return -1;
        else return Float.compare(
            original.applyAsFloat(first),
            original.applyAsFloat(second)
        );
    }

    @Override
    public boolean isNull(T object) {
        return isNull.test(object);
    }

    @Override
    public boolean isNotNull(T object) {
        return !isNull.test(object);
    }
}