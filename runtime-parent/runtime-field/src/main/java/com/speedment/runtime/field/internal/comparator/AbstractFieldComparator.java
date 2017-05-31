package com.speedment.runtime.field.internal.comparator;

import com.speedment.runtime.field.comparator.FieldComparator;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import static java.util.Collections.singletonList;

/**
 * Abstract base implementation of {@link FieldComparator}
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
abstract class AbstractFieldComparator<ENTITY>
implements FieldComparator<ENTITY> {

    AbstractFieldComparator() {}

    @Override
    public Comparator<ENTITY> thenComparing(Comparator<? super ENTITY> other) {
        return asCombined().thenComparing(other);
    }

    @Override
    public <U> Comparator<ENTITY> thenComparing(
            Function<? super ENTITY, ? extends U> keyExtractor,
            Comparator<? super U> keyComparator) {

        return asCombined().thenComparing(keyExtractor, keyComparator);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<ENTITY>
    thenComparing(Function<? super ENTITY, ? extends U> keyExtractor) {
        return asCombined().thenComparing(keyExtractor);
    }

    @Override
    public Comparator<ENTITY> thenComparingInt(
            ToIntFunction<? super ENTITY> keyExtractor) {
        return asCombined().thenComparingInt(keyExtractor);
    }

    @Override
    public Comparator<ENTITY> thenComparingLong(
            ToLongFunction<? super ENTITY> keyExtractor) {
        return asCombined().thenComparingLong(keyExtractor);
    }

    @Override
    public Comparator<ENTITY> thenComparingDouble(
            ToDoubleFunction<? super ENTITY> keyExtractor) {
        return asCombined().thenComparingDouble(keyExtractor);
    }

    private Comparator<ENTITY> asCombined() {
        return new CombinedComparatorImpl<>(singletonList(this));
    }
}
