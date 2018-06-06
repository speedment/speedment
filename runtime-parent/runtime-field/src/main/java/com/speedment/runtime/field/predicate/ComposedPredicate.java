package com.speedment.runtime.field.predicate;

import com.speedment.runtime.compute.expression.ComposedExpression;
import com.speedment.runtime.field.internal.predicate.ComposedPredicateImpl;

/**
 * SpeedmentPredicate that composes a {@code Function} and another
 * {@code SpeedmentPredicate}, testing the output of the function with the
 * predicate to get the result.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface ComposedPredicate<ENTITY, A>
extends SpeedmentPredicate<ENTITY>,
        ComposedExpression<ENTITY, A> {

    SpeedmentPredicate<A> secondStep();

    @Override
    default SpeedmentPredicate<ENTITY> negate() {
        return new ComposedPredicateImpl<>(firstStep(), secondStep().negate());
    }
}