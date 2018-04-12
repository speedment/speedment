package com.speedment.runtime.compute.internal;

import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToIntNullable;
import com.speedment.runtime.compute.expression.NullableExpression;

import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ToIntNullableImpl<T>
    implements NullableExpression<T, ToInt<T>>, ToIntNullable<T> {

    private final ToInt<T> original;
    private final Predicate<T> isNull;

    public ToIntNullableImpl(ToInt<T> original, Predicate<T> isNull) {
        this.original = requireNonNull(original);
        this.isNull   = requireNonNull(isNull);
    }

    @Override
    public ToInt<T> getInner() {
        return original;
    }

    @Override
    public Predicate<T> getIsNull() {
        return isNull;
    }

    @Override
    public Integer apply(T t) {
        return isNull.test(t) ? null : original.applyAsInt(t);
    }

    @Override
    public int applyAsInt(T t) throws NullPointerException {
        return original.applyAsInt(t);
    }

    @Override
    public ToInt<T> orThrow() throws NullPointerException {
        return original;
    }

    @Override
    public ToInt<T> orElseGet(ToInt<T> getter) {
        return t -> isNull.test(t)
            ? getter.applyAsInt(t)
            : original.applyAsInt(t);
    }

    @Override
    public ToInt<T> orElse(int value) {
        return t -> isNull.test(t) ? value : original.applyAsInt(t);
    }

    @Override
    public ToDoubleNullable<T> mapToDoubleIfPresent(IntToDoubleFunction mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsDouble(original.applyAsInt(t));
    }

    @Override
    public ToIntNullable<T> mapIfPresent(IntUnaryOperator mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsInt(original.applyAsInt(t));
    }

    @Override
    public long hash(T object) {
        return isNull.test(object)
            ? (0xffffffffL + 1) // Will never occur naturally
            : original.applyAsInt(object);
    }

    @Override
    public int compare(T first, T second) {
        final boolean f = isNull(first);
        final boolean s = isNull(second);
        if (f && s) return 0;
        else if (f) return 1;
        else if (s) return -1;
        else return Integer.compare(
            original.applyAsInt(first),
            original.applyAsInt(second)
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