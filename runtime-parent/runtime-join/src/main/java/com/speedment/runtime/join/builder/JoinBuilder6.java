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
package com.speedment.runtime.join.builder;

import com.speedment.common.function.Function6;
import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.TuplesOfNullables;
import com.speedment.common.tuple.nullable.Tuple6OfNullables;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.trait.HasDefaultBuild;
import com.speedment.runtime.join.trait.HasJoins;
import com.speedment.runtime.join.trait.HasOnPredicates;
import com.speedment.runtime.join.trait.HasWhere;

/**
 * Join Builder stage used when only 6 tables has been specified so far.
 *
 * @param <T0>  the first entity type
 * @param <T1>  the second entity type
 * @param <T2>  the third entity type
 * @param <T3>  the fourth entity type
 * @param <T4>  the fifth entity type
 * @param <T5>  the sixth entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since  3.1.1
 */
public interface JoinBuilder6<T0, T1, T2, T3, T4, T5>
    extends HasJoins<JoinBuilder6.AfterJoin<T0, T1, T2, T3, T4, T5, ?>, JoinBuilder7<T0, T1, T2, T3, T4, T5, ?>>,
            HasWhere<T5, JoinBuilder6<T0, T1, T2, T3, T4, T5>>,
            HasDefaultBuild<Tuple6OfNullables<T0, T1, T2, T3, T4, T5>> {

    @Override
    <T6> AfterJoin<T0, T1, T2, T3, T4, T5, T6> innerJoinOn(HasComparableOperators<T6, ?> joinedField);

    @Override
    <T6> AfterJoin<T0, T1, T2, T3, T4, T5, T6> leftJoinOn(HasComparableOperators<T6, ?> joinedField);

    @Override
    <T6> AfterJoin<T0, T1, T2, T3, T4, T5, T6> rightJoinOn(HasComparableOperators<T6, ?> joinedField);

    @Override
    <T6> JoinBuilder7<T0, T1, T2, T3, T4, T5, T6> crossJoin(TableIdentifier<T6> joinedTable);

    @Override
    default Join<Tuple6OfNullables<T0, T1, T2, T3, T4, T5>> build() {
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
    <T> Join<T> build(Function6<T0, T1, T2, T3, T4, T5, T> constructor);

    interface AfterJoin<T0, T1, T2, T3, T4, T5, T6>
    extends HasOnPredicates<JoinBuilder7<T0, T1, T2, T3, T4, T5, T6>> {}
}
