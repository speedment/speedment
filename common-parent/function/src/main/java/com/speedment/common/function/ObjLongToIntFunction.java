package com.speedment.common.function;

/**
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface ObjLongToIntFunction<T> {

    int applyAsInt(T object, long value);

}

