package com.speedment.runtime.compute.internal;

import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.compute.ToIntNullable;
import com.speedment.runtime.compute.ToStringNullable;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ToEnumNullableImpl<T, E extends Enum<E>>
    implements ToEnumNullable<T, E> {

    private final Class<E> enumClass;
    private final Function<T, E> inner;

    public ToEnumNullableImpl(Class<E> enumClass, Function<T, E> inner) {
        this.enumClass = requireNonNull(enumClass);
        this.inner     = requireNonNull(inner);
    }

    @Override
    public Class<E> enumClass() {
        return enumClass;
    }

    @Override
    public E apply(T t) {
        return inner.apply(t);
    }

    @Override
    public ToIntNullable<T> asOrdinal() {
        return object -> isNotNull(object)
            ? apply(object).ordinal()
            : null;
    }

    @Override
    public ToStringNullable<T> asName() {
        return object -> isNotNull(object)
            ? apply(object).name()
            : null;
    }

    @Override
    public long hash(T object) {
        return object == null ? 0 : object.hashCode();
    }

    @Override
    public int compare(T first, T second) {
        final E f = apply(first);
        final E s = apply(second);
        if (f == null && s == null) {
            return 0;
        } else if (f == null) {
            return 1;
        } else if (s == null) {
            return -1;
        } else {
            return Integer.compare(f.ordinal(), s.ordinal());
        }
    }
}