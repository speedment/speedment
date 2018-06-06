package com.speedment.runtime.field.internal.predicate;

import com.speedment.runtime.field.predicate.ComposedPredicate;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link ComposedPredicate}
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public class ComposedPredicateImpl<T, A> implements ComposedPredicate<T, A> {

    private final Function<? super T, ? extends A> firstStep;
    private final SpeedmentPredicate<A> secondStep;

    public ComposedPredicateImpl(Function<? super T, ? extends A> firstStep,
                                 SpeedmentPredicate<A> secondStep) {
        this.firstStep  = requireNonNull(firstStep);
        this.secondStep = requireNonNull(secondStep);
    }

    @Override
    public Function<T, A> firstStep() {
        @SuppressWarnings("unchecked")
        final Function<T, A> function = (Function<T, A>) firstStep;
        return function;
    }

    @Override
    public SpeedmentPredicate<A> secondStep() {
        return secondStep;
    }

    @Override
    public boolean applyAsBoolean(T object) {
        return secondStep.applyAsBoolean(firstStep.apply(object));
    }
}