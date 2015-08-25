package com.speedment.core.field.longs;

import java.util.function.ToLongFunction;

/**
 * @author Emil Forslund
 * @param <ENTITY> The entity type
 */
public interface LongGetter<ENTITY> extends ToLongFunction<ENTITY> {}