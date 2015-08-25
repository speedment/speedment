package com.speedment.core.field.ints;

/**
 * @author Emil Forslund
 * @param <ENTITY> The entity type
 */
public interface IntSetter<ENTITY> {
    ENTITY applyAsInt(ENTITY entity, int value);
}