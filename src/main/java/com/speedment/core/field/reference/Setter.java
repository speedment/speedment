package com.speedment.core.field.reference;

import java.util.function.BiFunction;

/**
 * @author Emil Forslund
 * @param <ENTITY> the entity
 * @param <V> the type of the value to return
 */
public interface Setter<ENTITY, V> extends BiFunction<ENTITY, V, ENTITY> {}