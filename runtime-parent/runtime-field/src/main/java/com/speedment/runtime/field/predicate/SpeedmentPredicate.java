package com.speedment.runtime.field.predicate;

import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.field.internal.predicate.ComposedPredicateImpl;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Common interface for all metadata-rich predicates in the Speedment library.
 * Speedment predicates typically operates on an entity from a manager generated
 * by Speedment.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface SpeedmentPredicate<ENTITY> extends ToBoolean<ENTITY> {

    @Override
    default <T> ComposedPredicate<T, ENTITY>
    compose(Function<? super T, ? extends ENTITY> before) {
        return new ComposedPredicateImpl<>(before, this);
    }

    @Override
    default SpeedmentPredicate<ENTITY> negate() {
        return (t) -> !test(t);
    }

    @Override
    default SpeedmentPredicate<ENTITY> and(Predicate<? super ENTITY> other) {
        return CombinedPredicate.and(this, other);
    }

    @Override
    default SpeedmentPredicate<ENTITY> or(Predicate<? super ENTITY> other) {
        return CombinedPredicate.or(this, other);
    }
}