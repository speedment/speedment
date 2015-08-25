package com.speedment.core.field.reference;

import java.util.function.Function;

/**
 * @author Emil Forslund
 * @param <ENTITY> the entity
 * @param <V> the type of the value to return
 */
public interface Getter<ENTITY, V> extends Function<ENTITY, V> {}