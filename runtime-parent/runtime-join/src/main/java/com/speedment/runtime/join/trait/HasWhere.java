package com.speedment.runtime.join.trait;

import com.speedment.runtime.field.predicate.FieldPredicate;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> entity type
 * @param <R> return type
 */
public interface HasWhere<ENTITY, R> {

    /**
     * Adds the provided {@code  predicate} to the collection of predicates that
     * shall be applied to entities from the previous Manager before entities
     * are accepted in the join.
     * <p>
     * Currently, only {@link FieldPredicate FieldPredicates } obtained by
     * Speedment predicate builders can be used in Join Operations. No anonymous
     * lambdas can be used.
     *
     * @param predicate to apply
     * @return a builder where the provided {@code predicate} is added
     *
     * @throws NullPointerException if the provided {@code predicate } is
     * {@code null}
     */
    R where(Predicate<? super ENTITY> predicate);

}
