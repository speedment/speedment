package com.speedment.runtime.join.builder;

import com.speedment.common.function.Function5;
import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.TuplesOfNullables;
import com.speedment.common.tuple.nullable.Tuple5OfNullables;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.trait.HasDefaultBuild;
import com.speedment.runtime.join.trait.HasJoins;
import com.speedment.runtime.join.trait.HasOnPredicates;
import com.speedment.runtime.join.trait.HasWhere;

/**
 * Join Builder stage used when only 5 tables has been specified so far.
 *
 * @param <T0>  the first entity type
 * @param <T1>  the second entity type
 * @param <T2>  the third entity type
 * @param <T3>  the fourth entity type
 * @param <T4>  the fifth entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since  3.1.1
 */
public interface JoinBuilder5<T0, T1, T2, T3, T4>
extends HasJoins<JoinBuilder5.AfterJoin<T0, T1, T2, T3, T4, ?>, JoinBuilder6<T0, T1, T2, T3, T4, ?>>,
        HasWhere<T4, JoinBuilder5<T0, T1, T2, T3, T4>>,
        HasDefaultBuild<Tuple5OfNullables<T0, T1, T2, T3, T4>> {

    @Override
    <T5> AfterJoin<T0, T1, T2, T3, T4, T5> innerJoinOn(HasComparableOperators<T5, ?> joinedField);

    @Override
    <T5> AfterJoin<T0, T1, T2, T3, T4, T5> leftJoinOn(HasComparableOperators<T5, ?> joinedField);

    @Override
    <T5> AfterJoin<T0, T1, T2, T3, T4, T5> rightJoinOn(HasComparableOperators<T5, ?> joinedField);

//                        @Override
//                        <T5> AfterJoin<T0, T1, T2, T3, T4, T5> fullOuterJoinOn(HasComparableOperators<T5, ?> joinedField);

    @Override
    <T5> JoinBuilder6<T0, T1, T2, T3, T4, T5> crossJoin(TableIdentifier<T5> joinedTable);

    @Override
    default Join<Tuple5OfNullables<T0, T1, T2, T3, T4>> build() {
        return build(TuplesOfNullables::ofNullables);
    }

    /**
     * Creates and returns a new Join object where elements in the Join
     * object's stream method is created using the provided {@code
     * constructor}.
     *
     * @param <T>         the type of element in the Join object's stream
     *                    method.
     * @param constructor to use to create stream elements.
     * @return a new Join object where elements in the Join object's stream
     *     method is of a default {@link Tuple} type
     *
     * @throws NullPointerException  if the provided {@code constructor } is
     *                               {@code null}
     * @throws IllegalStateException if fields that are added via the {@code
     *                               on() } method refers to tables that are
     *                               not a part of the join.
     */
    <T> Join<T> build(Function5<T0, T1, T2, T3, T4, T> constructor);

    interface AfterJoin<T0, T1, T2, T3, T4, T5>
    extends HasOnPredicates<JoinBuilder6<T0, T1, T2, T3, T4, T5>> {}
}
