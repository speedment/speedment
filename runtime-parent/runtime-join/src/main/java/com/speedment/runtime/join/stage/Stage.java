/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.join.stage;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.internal.stage.StageImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type of entities
 */
public interface Stage<ENTITY> {

    /**
     * Returns the TableIdentifier for this Stage.
     *
     * @return the TableIdentifier for this Stage
     */
    TableIdentifier<ENTITY> identifier();

    /**
     * Returns an unmodifiable list of predicates that shall be applied for this
     * Stage. If no predicates are to be applied, an empty List is returned.
     *
     * @return a list of predicates that shall be applied for this Stage
     */
    List<Predicate<? super ENTITY>> predicates();

    /**
     * Returns the JoinType for this Stage, or {@code empty()} if no JoinType is
     * defined (i.e. for the first Stage).
     *
     * @return the JoinType for this Stage, or {@code empty()} if no JoinType is
     * defined.
     */
    Optional<JoinType> joinType();

    /**
     * Returns a Field that belongs to the table of this Stage, or
     * {@code empty()} if no Field is defined (i.e. for a CROSS JOIN).
     *
     * @return a Field that belongs to the table of this Stage,
     * or{@code empty()} if no Field is defined
     */
    Optional<HasComparableOperators<ENTITY, ?>> field();

    /**
     * Returns the OperatorType for this Stage or , or {@code empty()} if no
     * OperatorType is defined (i.e. for a CROSS JOIN).
     *
     * @return the OperatorType for this Stage or , or {@code empty()}if no
     * OperatorType is defined (i.e. for a CROSS JOIN)
     */
    Optional<JoinOperator> joinOperator();

    /**
     * Returns a Field that belongs to a table from a previous stage, or
     * {@code empty()} if no Field is defined (i.e. for a CROSS JOIN).
     *
     * @return a Field that belongs to a table from a previous stage, or
     * {@code empty()} if no Field is defined
     */
    Optional<HasComparableOperators<?, ?>> foreignField();

    /**
     * Returns which stage index this stage references or -1. More formally, the
     * method returns the index to a stage where this stage {@link #foreignField()}
     * reference a stage's {@link #field()} or if no field() exists, where this stage
     * (if not aliased) references a stage's {@link #identifier()}. If no such stage
     * exist (e.g. for stage 0 or cross joined tables) then the method returns -1.
     *
     * @return which stage index this stage references or -1
     */
    int referencedStage();

//    /**
//     * Returns a Field that belongs to the table of this Stage, or
//     * {@code empty()} if no Field is defined (i.e. for a CROSS JOIN or for all
//     * OperatorTypes that are not BETWEEN or NOT_BETWEEN).
//     *
//     * @return a Field that belongs to the table of this Stage, or
//     * {@code empty()} if no Field is defined
//     */
//    Optional<HasComparableOperators<?, ?>> foreignSecondField();
//
//    /**
//     * Returns the Inclusion type for this Stage or {@code empty()} if no
//     * Inclusion type is defined (i.e. for a CROSS JOIN or for all OperatorTypes
//     * that are not BETWEEN or NOT_BETWEEN).
//     *
//     * @return the Inclusion type for this stage, or {@code empty()} if no
//     * Inclusion type is defined
//     */
//    Optional<Inclusion> foreignInclusion();
    
    /**
     * Creates and returns a mew default implementation of a Stage.
     * @param <T> type
     * @param identifier of the table
     * @param predicates to apply on entities from this table
     * @param joinType that shall be applied (nullable)
     * @param field from the table to use (nullable)
     * @param joinOperator to use when joining (nullable)
     * @param foreignField from another table (nullable)
     * @param referencedStage the previous stage that is referenced by this stage
     * @return a mew default implementation of a Stage
     */
    static <T> Stage<T> of(
        final TableIdentifier<T> identifier,
        final List<Predicate<? super T>> predicates,
        final JoinType joinType,
        final HasComparableOperators<T, ?> field,
        final JoinOperator joinOperator,
        final HasComparableOperators<?, ?> foreignField,
        final int referencedStage
    ) {
        return new StageImpl<>(identifier, predicates, joinType, field, joinOperator, foreignField, referencedStage);
    }

}
