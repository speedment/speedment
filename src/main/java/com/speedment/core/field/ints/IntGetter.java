package com.speedment.core.field.ints;

import java.util.function.ToIntFunction;

/**
 * @author Emil Forslund
 * @param <ENTITY> The entity type
 */
public interface IntGetter<ENTITY> extends ToIntFunction<ENTITY> {}