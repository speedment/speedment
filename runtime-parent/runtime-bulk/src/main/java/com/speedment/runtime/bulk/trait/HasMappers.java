package com.speedment.runtime.bulk.trait;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> types
 */
public interface HasMappers<ENTITY> {

    Stream<Function<? super ENTITY, ? extends ENTITY>> mappers();
}
