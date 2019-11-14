package com.speedment.common.tuple.internal;

import com.speedment.common.tuple.TupleMapper;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public abstract class AbstractTupleMapper<T, R> implements TupleMapper<T, R> {

    private final Function<T, ?>[] mappers;

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected AbstractTupleMapper(int degree) {
        this.mappers = new Function[degree];
    }

    @Override
    public final int degree() {
        return mappers.length;
    }

    @Override
    public final Function<T, ?> get(int index) {
        return mappers[index];
    }

    @SuppressWarnings("unchecked")
    protected final <C> Function<T, C> getAndCast(int index) {
        return (Function<T, C>) mappers[index];
    }

    protected final void set(int index, Function<T, ?> mapper) {
        mappers[index] = requireNonNull(mapper);
    }

}
