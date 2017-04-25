package com.speedment.runtime.field.predicate;

import com.speedment.runtime.field.internal.predicate.AbstractCombinedPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Aggregation of a number of {@link Predicate Predicates} of the same type
 * (e.g. AND or OR) that can be applied in combination.
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 */
public interface CombinedPredicate<ENTITY> extends Predicate<ENTITY> {

    /**
     * This enum list all the different types of combinations
     */
    public enum Type {
        AND, OR
    }

    /**
     * Creates and returns a {link Stream} of all predicates that this
     * CombinedPredicate holds.
     *
     * @return a {link Stream} of all predicates that this CombinedPredicate
     * holds
     */
    Stream<Predicate<? super ENTITY>> stream();

    /**
     * Returns the number of predicates that this CombinedBasePredicate holds
     *
     * @return the number of predicates that this CombinedBasePredicate holds
     */
    int size();

    /**
     * Returns the {@link Type} of this CombinedBasePredicate
     *
     * @return the {@link Type} of this CombinedBasePredicate
     */
    Type getType();

    @Override
    public CombinedPredicate<ENTITY> and(Predicate<? super ENTITY> other);

    @Override
    public CombinedPredicate<ENTITY> or(Predicate<? super ENTITY> other);
    
    
    static <ENTITY> CombinedPredicate<ENTITY> and(Predicate<ENTITY> first, Predicate<? super ENTITY> second) {
        return new AbstractCombinedPredicate.AndCombinedBasePredicate<>(first, second);
    }

    static <ENTITY> CombinedPredicate<ENTITY> or(Predicate<ENTITY> first, Predicate<? super ENTITY> second) {
        return new AbstractCombinedPredicate.OrCombinedBasePredicate<>(first, second);
    }

}
