package com.speedment.runtime.bulk.trait;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> types
 */
public interface HasPredicates<ENTITY> {

    Stream<Predicate<? super ENTITY>> predicates();
}
