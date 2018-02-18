package com.speedment.runtime.join.stage;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 */
public interface Stage<T> {

    /**
     * Returns the TableIdentifier for this Stage.
     *
     * @return the TableIdentifier for this Stage
     */
    TableIdentifier<T> identifier();

    /**
     * Returns an unmodifiable list of predicates that shall be applied for this
     * Stage. If no predicates are to be applied, an empty List is returned.
     *
     * @return a list of predicates that shall be applied for this Stage
     */
    List<Predicate<? super T>> predicates();

    /**
     * Returns the JoinType for this Stage, or {@code empty()} if no JoinType is
     * defined (i.e. for the first Stage).
     *
     * @return the JoinType for this Stage, or {@code empty()} if no JoinType is
     * defined.
     */
    Optional<JoinType> joinType();

    /**
     * Returns a Field that belongs to a table from a previous stage, or
     * {@code empty()} if no Field is defined (i.e. for a CROSS JOIN).
     *
     * @return a Field that belongs to a table from a previous stage, or
     * {@code empty()} if no Field is defined
     */
    Optional<HasComparableOperators<?, ?>> otherTableField();

    /**
     * Returns the OperatorType for this Stage or , or {@code empty()} if no
     * OperatorType is defined (i.e. for a CROSS JOIN).
     *
     * @return the OperatorType for this Stage or , or {@code empty()}if no
     * OperatorType is defined (i.e. for a CROSS JOIN)
     */
    Optional<OperatorType> operatorType();

    /**
     * Returns a Field that belongs to the table of this Stage, or
     * {@code empty()} if no Field is defined (i.e. for a CROSS JOIN).
     *
     * @return a Field that belongs to the table of this Stage,
     * or{@code empty()} if no Field is defined
     */
    Optional<HasComparableOperators<? extends T, ?>> firstField();

    /**
     * Returns a Field that belongs to the table of this Stage, or
     * {@code empty()} if no Field is defined (i.e. for a CROSS JOIN or for all
     * OperatorTypes that are not BETWEEN or NOT_BETWEEN).
     *
     * @return a Field that belongs to the table of this Stage, or
     * {@code empty()} if no Field is defined
     */
    Optional<HasComparableOperators<? extends T, ?>> secondField();

}
