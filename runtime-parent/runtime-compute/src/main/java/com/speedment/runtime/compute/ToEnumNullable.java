package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.ToEnumImpl;
import com.speedment.runtime.compute.internal.ToEnumNullableImpl;
import com.speedment.runtime.compute.internal.expression.OrElseGetUtil;
import com.speedment.runtime.compute.internal.expression.OrElseThrowUtil;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.ToNullable;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Expression that given an entity returns an {@code enum} value, or
 * {@code null}. The expression also knows about the enum class and can
 * therefore not be implemented as a lambda like the other expressions in this
 * module.
 *
 * @param <T> type to extract from
 * @param <E> enum type
 *
 * @see Function
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
public interface ToEnumNullable<T, E extends Enum<E>>
extends Expression,
    ToNullable<T, E>,
    HasHash<T>,
    HasCompare<T> {

    static <T, E extends Enum<E>> ToEnumNullable<T, E>
        toEnumNullable(Class<E> enumClass, Function<T, E> getter) {
        return new ToEnumNullableImpl<>(enumClass, getter);
    }

    Class<E> enumClass();

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.ENUM_NULLABLE;
    }

    default ToIntNullable<T> asOrdinal() {
        return t -> isNotNull(t)
            ? apply(t).ordinal()
            : null;
    }

    default ToStringNullable<T> asName() {
        return t -> isNotNull(t)
            ? apply(t).name()
            : null;
    }

    default ToEnum<T, E> orElseThrow() {
        return OrElseThrowUtil.orElseThrow(this);
    }

    default ToEnum<T, E> orElseGet(ToEnum<T, E> getter) {
        return OrElseGetUtil.orElseGet(this, getter);
    }

    default ToEnum<T, E> orElse(E value) {
        return OrElseUtil.orElse(this, value);
    }

    default ToDoubleNullable<T> mapToDoubleIfPresent(ToDouble<E> mapper) {
        final ToEnumNullable<T, E> delegate = this;
        return new ToDoubleNullable<T>() {
            @Override
            public Double apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.applyAsDouble(delegate.apply(object));
            }

            @Override
            public double applyAsDouble(T object) throws NullPointerException {
                return mapper.applyAsDouble(delegate.apply(object));
            }

            @Override
            public ToDouble<T> orElseGet(ToDouble<T> getter) {
                return object -> delegate.isNull(object)
                    ? getter.applyAsDouble(object)
                    : mapper.applyAsDouble(delegate.apply(object));
            }

            @Override
            public ToDouble<T> orElse(double value) {
                return object -> delegate.isNull(object)
                    ? value
                    : mapper.applyAsDouble(delegate.apply(object));
            }

            @Override
            public boolean isNull(T object) {
                return delegate.isNull(object);
            }

            @Override
            public boolean isNotNull(T object) {
                return delegate.isNotNull(object);
            }
        };
    }

    default ToEnumNullable<T, E> mapIfPresent(UnaryOperator<E> mapper) {
        final ToEnumNullable<T, E> delegate = this;

        return new ToEnumNullable<T, E>() {

            @Override
            public Class<E> enumClass() {
                return delegate.enumClass();
            }

            @Override
            public E apply(T object) {
                return delegate.isNull(object) ? null
                    : mapper.apply(delegate.apply(object));
            }

            @Override
            public ToEnum<T, E> orElseGet(ToEnum<T, E> getter) {
                return new ToEnumImpl<>(
                    delegate.enumClass(),
                    object -> delegate.isNull(object)
                    ? getter.apply(object)
                    : mapper.apply(delegate.apply(object))
                );
            }

            @Override
            public ToEnum<T, E> orElse(E value) {
                return new ToEnumImpl<>(
                    delegate.enumClass(),
                    object -> delegate.isNull(object)
                    ? value
                    : mapper.apply(delegate.apply(object))
                );
            }

            @Override
            public boolean isNull(T object) {
                return delegate.isNull(object);
            }

            @Override
            public boolean isNotNull(T object) {
                return delegate.isNotNull(object);
            }
        };
    }

    @Override
    default long hash(T object) {
        final E e = apply(object);
        return e == null ? -1 : e.hashCode();
    }

    @Override
    default int compare(T first, T second) {
        final E a = apply(first);
        final E b = apply(second);
        if (a == null) {
            return (b == null) ? -1 : 0;
        } else {
            return a.compareTo(b);
        }
    }
}
