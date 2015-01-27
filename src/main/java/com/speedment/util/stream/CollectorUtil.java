package com.speedment.util.stream;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;

/**
 *
 * @author pemi
 */
public class CollectorUtil {

    private CollectorUtil() {
    }

    public static <T> Collector<T, Set<T>, Set<T>> toUnmodifiableSet() {
        return Collector.of(HashSet::new, Set::add, (left, right) -> {
            left.addAll(right);
            return left;
        }, Collections::unmodifiableSet, Collector.Characteristics.UNORDERED);
    }

}
