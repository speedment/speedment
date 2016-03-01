package com.speedment.internal.core.stream.builder.action.trait;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 */
public interface HasPredicate<T> {

    Predicate<? super T> getPredicate();

}
