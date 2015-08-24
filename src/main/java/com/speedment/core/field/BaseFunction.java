package com.speedment.core.field;

import java.util.function.Function;

/**
 *
 * @author Emil
 * @param <ENTITY> the entity
 */
public abstract class BaseFunction<ENTITY> implements Function<ENTITY, ENTITY> {

    @Override
    public <V> Function<V, ENTITY> compose(Function<? super V, ? extends ENTITY> before) {
        throw new UnsupportedOperationException("Optimized functions does not support 'compose'.");
    }

    @Override
    public <V> Function<ENTITY, V> andThen(Function<? super ENTITY, ? extends V> after) {
        throw new UnsupportedOperationException("Optimized functions does not support 'andThen'.");
    }
}