package com.speedment.runtime.core.internal.stream.autoclose;

import java.util.Objects;
import java.util.function.UnaryOperator;

interface NamedUnaryOperator<T> extends UnaryOperator<T>, HasName {

    static <T, R> NamedUnaryOperator<T> of(String name, UnaryOperator<T> function) {
        return new NamedUnaryOperatorImpl<>(name, function);
    }

    default NamedUnaryOperator<T> andThenNamed(NamedUnaryOperator<T> after) {
        Objects.requireNonNull(after);
        return new NamedUnaryOperatorImpl<>(name() + "." + after.name(), (T t) -> after.apply(apply(t)));
    }

}