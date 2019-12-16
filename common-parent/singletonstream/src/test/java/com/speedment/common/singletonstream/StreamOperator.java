package com.speedment.common.singletonstream;

import java.util.function.UnaryOperator;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

final class StreamOperator<T, S extends BaseStream<T, S>> implements UnaryOperator<S>  {

    private final String name;
    private final UnaryOperator<S> inner;

    StreamOperator(String name, UnaryOperator<S> inner) {
        this.name  = requireNonNull(name);
        this.inner = requireNonNull(inner);
    }

    @Override
    public S apply(S s) {
        return inner.apply(s);
    }

    String name() {
        return name;
    }

    static <T, S extends BaseStream<T, S>> StreamOperator<T, S> create(String name, UnaryOperator<S> operator) {
        return new StreamOperator<>(name, operator);
    }

}