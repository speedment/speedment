package com.speedment.runtime.core.internal.stream.autoclose;

import java.util.function.UnaryOperator;

import static java.util.Objects.requireNonNull;

final class NamedUnaryOperatorImpl<T> extends HasNameImpl implements NamedUnaryOperator<T> {

    private final UnaryOperator<T> function;

    NamedUnaryOperatorImpl(String name, UnaryOperator<T> function) {
        super(name);
        this.function = requireNonNull(function);
    }

    @Override
    public T apply(T t) {
        return function.apply(t);
    }

}
