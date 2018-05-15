package com.speedment.runtime.join.builder;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.trait.HasJoins;
import com.speedment.runtime.join.trait.HasOnPredicates;
import com.speedment.runtime.join.trait.HasWhere;

/**
 * Join Builder stage used when only 1 table has been specified so far.
 *
 * @param <T0>  the first entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since  3.1.1
 */
public interface JoinBuilder1<T0>
extends HasJoins<JoinBuilder1.AfterJoin<T0, ?>, JoinBuilder2<T0, ?>>,
        HasWhere<T0, JoinBuilder1<T0>> {

    @Override
    <T1> AfterJoin<T0, T1> innerJoinOn(HasComparableOperators<T1, ?> joinedField);

    @Override
    <T1> AfterJoin<T0, T1> leftJoinOn(HasComparableOperators<T1, ?> joinedField);

    @Override
    <T1> AfterJoin<T0, T1> rightJoinOn(HasComparableOperators<T1, ?> joinedField);

//        @Override
//        <T1> AfterJoin<T0, T1> fullOuterJoinOn(HasComparableOperators<T1, ?> joinedField);

    @Override
    <T1> JoinBuilder2<T0, T1> crossJoin(TableIdentifier<T1> joinedTable);

    interface AfterJoin<T0, T1> extends HasOnPredicates<JoinBuilder2<T0, T1>> {}
}