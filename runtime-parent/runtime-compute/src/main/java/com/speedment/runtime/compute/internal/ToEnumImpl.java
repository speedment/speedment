package com.speedment.runtime.compute.internal;

import com.speedment.runtime.compute.ToEnum;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToString;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ToEnumImpl<T, E extends Enum<E>> implements ToEnum<T, E> {

    private final Class<E> enumClass;
    private final Function<T, E> inner;

    public ToEnumImpl(Class<E> enumClass, Function<T, E> inner) {
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
    public ToInt<T> asOrdinal() {
        return object -> apply(object).ordinal();
    }

    @Override
    public ToString<T> asName() {
        return object -> apply(object).name();
    }

    @Override
    public long hash(T object) {
        return object.hashCode();
    }

    @Override
    public int compare(T first, T second) {
        return Integer.compare(
            apply(first).ordinal(),
            apply(second).ordinal()
        );
    }
}