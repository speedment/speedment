package com.speedment.core.field.doubles;

import java.util.function.ToDoubleFunction;

/**
 * @author Emil Forslund
 * @param <ENTITY> The entity type
 */
public interface DoubleGetter<ENTITY> extends ToDoubleFunction<ENTITY> {}