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

import com.speedment.common.function.TriFunction;
import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.TuplesOfNullables;
import com.speedment.common.tuple.nullable.Tuple3OfNullables;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.trait.HasDefaultBuild;
import com.speedment.runtime.join.trait.HasJoins;
import com.speedment.runtime.join.trait.HasOnPredicates;
import com.speedment.runtime.join.trait.HasWhere;

/**
 * Join Builder stage used when only 3 tables has been specified so far.
 *
 * @param <T0>  the first entity type
 * @param <T1>  the second entity type
 * @param <T2>  the third entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since  3.1.1
 */
public interface JoinBuilder3<T0, T1, T2>
    extends HasJoins<JoinBuilder3.AfterJoin<T0, T1, T2, ?>, JoinBuilder4<T0, T1, T2, ?>>,
            HasWhere<T2, JoinBuilder3<T0, T1, T2>>,
            HasDefaultBuild<Tuple3OfNullables<T0, T1, T2>> {

    @Override
    <T3> AfterJoin<T0, T1, T2, T3> innerJoinOn(HasComparableOperators<T3, ?> joinedField);

    @Override
    <T3> AfterJoin<T0, T1, T2, T3> leftJoinOn(HasComparableOperators<T3, ?> joinedField);

    @Override
    <T3> AfterJoin<T0, T1, T2, T3> rightJoinOn(HasComparableOperators<T3, ?> joinedField);

    @Override
    <T3> JoinBuilder4<T0, T1, T2, T3> crossJoin(TableIdentifier<T3> joinedTable);

    @Override
    default Join<Tuple3OfNullables<T0, T1, T2>> build() {
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
     * @throws NullPointerException if the provided {@code constructor } is
     *                              {@code null}
     */
    <T> Join<T> build(TriFunction<T0, T1, T2, T> constructor);

    interface AfterJoin<T0, T1, T2, T3>
    extends HasOnPredicates<JoinBuilder4<T0, T1, T2, T3>> {}
}
