package com.speedment.runtime.join.trait;

import com.speedment.runtime.join.JoinComponent;
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
     * shall be applied to the data source before entities are accepted in the
     * join.
     *
     * @param predicate to apply
     * @return a builder where the provided {@code predicate} is added
     * @throws NullPointerException if the provided {@code predicate } is
     * {@code null}
     */
    R where(Predicate<? super ENTITY> predicate);

}
