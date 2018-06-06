package com.speedment.runtime.compute.expression.predicate;

/**
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface IsNotNull<T, R>
extends NullPredicate<T, R> {

    @Override
    default NullPredicateType nullPredicateType() {
        return NullPredicateType.IS_NOT_NULL;
    }

    @Override
    default boolean test(T in) {
        final R result = expression().apply(in);
        return result != null;
    }

    @Override
    IsNull<T, R> negate();
}