package com.speedment.runtime.core.internal.stream.autoclose;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

final class NamedFunctionImpl<T, R> extends HasNameImpl implements NamedFunction<T, R> {

    private final Function<T, R> function;

    NamedFunctionImpl(String name, Function<T, R> function) {
        super(name);
        this.function = requireNonNull(function);
    }

    @Override
    public R apply(T t) {
        return function.apply(t);
    }
}