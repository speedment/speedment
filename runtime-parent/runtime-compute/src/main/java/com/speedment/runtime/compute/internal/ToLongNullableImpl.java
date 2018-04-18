package com.speedment.runtime.compute.internal;

import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToLongNullable;
import com.speedment.runtime.compute.expression.NullableExpression;

import java.util.Objects;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ToLongNullableImpl<T>
    implements NullableExpression<T, ToLong<T>>, ToLongNullable<T> {

    private final ToLong<T> original;
    private final Predicate<T> isNull;

    public ToLongNullableImpl(ToLong<T> original, Predicate<T> isNull) {
        this.original = requireNonNull(original);
        this.isNull   = requireNonNull(isNull);
    }

    @Override
    public ToLong<T> getInner() {
        return original;
    }

    @Override
    public Predicate<T> getIsNull() {
        return isNull;
    }

    @Override
    public Long apply(T t) {
        return isNull.test(t) ? null : original.applyAsLong(t);
    }

    @Override
    public long applyAsLong(T t) throws NullPointerException {
        return original.applyAsLong(t);
    }

    @Override
    public ToLong<T> orThrow() throws NullPointerException {
        return original;
    }

    @Override
    public ToLong<T> orElseGet(ToLong<T> getter) {
        return t -> isNull.test(t)
            ? getter.applyAsLong(t)
            : original.applyAsLong(t);
    }

    @Override
    public ToLong<T> orElse(long value) {
        return t -> isNull.test(t) ? value : original.applyAsLong(t);
    }

    @Override
    public ToDoubleNullable<T> mapToDoubleIfPresent(LongToDoubleFunction mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsDouble(original.applyAsLong(t));
    }

    @Override
    public ToLongNullable<T> mapIfPresent(LongUnaryOperator mapper) {
        return t -> isNull.test(t) ? null
            : mapper.applyAsLong(original.applyAsLong(t));
    }

    @Override
    public long hash(T object) {
        return isNull.test(object)
            ? 0xffffffffffffffffL
            : original.applyAsLong(object);
    }

    @Override
    public int compare(T first, T second) {
        final boolean f = isNull(first);
        final boolean s = isNull(second);
        if (f && s) return 0;
        else if (f) return 1;
        else if (s) return -1;
        else return Long.compare(
            original.applyAsLong(first),
            original.applyAsLong(second)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof NullableExpression)) return false;
        final NullableExpression<?, ?> that = (NullableExpression<?, ?>) o;
        return Objects.equals(original, that.getInner()) &&
            Objects.equals(isNull, that.getIsNull());
    }

    @Override
    public int hashCode() {
        return Objects.hash(original, isNull);
    }
}