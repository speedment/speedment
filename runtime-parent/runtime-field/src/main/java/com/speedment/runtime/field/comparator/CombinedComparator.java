package com.speedment.runtime.field.comparator;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * A combined {@link Comparator} that compares a number of
 * {@link FieldComparator FieldComparators} in sequence.
 *
 * @param <ENTITY>  the entity type
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public interface CombinedComparator<ENTITY> extends Comparator<ENTITY>  {

    @Override
    CombinedComparator<ENTITY> reversed();

    /**
     * Returns the comparators ordered so that the first comparator is
     * the most significant, and if that evaluates to {@code 0}, continue on to
     * the next one.
     *
     * @return  list of comparators
     */
    Stream<FieldComparator<? super ENTITY>> stream();

    /**
     * The number of comparators in the {@link #stream()}.
     *
     * @return  the number of comparators
     */
    int size();
}