package com.speedment.core.field.longs;

/**
 * @author Emil Forslund
 * @param <ENTITY> The entity type
 */
public interface LongSetter<ENTITY> {
    ENTITY applyAsLong(ENTITY entity, long value);
}
