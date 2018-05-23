package com.speedment.runtime.compute.expression.predicate;

import com.speedment.runtime.compute.expression.Expression;

/**
 * @author Emil Forslund
 * @since  1.2.1
 */
public interface IsNotNull<T, R, NON_NULLABLE extends Expression<T>>
extends NullPredicate<T, R, NON_NULLABLE> {

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
    IsNull<T, R, NON_NULLABLE> negate();
}