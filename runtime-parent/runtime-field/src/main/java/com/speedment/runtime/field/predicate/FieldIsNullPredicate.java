package com.speedment.runtime.field.predicate;

import com.speedment.runtime.compute.expression.predicate.IsNotNull;
import com.speedment.runtime.compute.expression.predicate.IsNull;

/**
 * Specialized {@link FieldPredicate} that also implements {@link IsNotNull}
 * from the {@code runtime-compute}-module.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface FieldIsNullPredicate<ENTITY, T>
extends FieldPredicate<ENTITY>, IsNull<ENTITY, T> {

    @Override
    boolean test(ENTITY value);

    @Override
    FieldIsNotNullPredicate<ENTITY, T> negate();

    @Override
    default PredicateType getPredicateType() {
        return PredicateType.IS_NULL;
    }

    @Override
    default boolean applyAsBoolean(ENTITY object) {
        return test(object);
    }
}