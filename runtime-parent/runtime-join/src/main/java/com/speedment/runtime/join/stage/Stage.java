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
     * @return a mew default implementation of a Stage
     */
    static <T> Stage<T> of(
        final TableIdentifier<T> identifier,
        final List<Predicate<? super T>> predicates,
        final JoinType joinType,
        final HasComparableOperators<T, ?> field,
        final JoinOperator joinOperator,
        final HasComparableOperators<?, ?> foreignField
    ) {
        return new StageImpl<>(identifier, predicates, joinType, field, joinOperator, foreignField);
    }

}
