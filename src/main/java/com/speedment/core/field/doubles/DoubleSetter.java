package com.speedment.core.field.doubles;

/**
 * @author Emil Forslund
 * @param <ENTITY> The entity type
 */
public interface DoubleSetter<ENTITY> {
    ENTITY applyAsDouble(ENTITY entity, double value);
}