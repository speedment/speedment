package com.speedment.runtime.core.internal.stream.autoclose;

import java.util.function.Function;

interface NamedFunction<T, R> extends Function<T, R>, HasName {

    static <T, R> NamedFunction<T, R> of(String name, Function<T, R> function) {
        return new NamedFunctionImpl<>(name, function);
    }

}
