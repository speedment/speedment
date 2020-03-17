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

import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.TuplesOfNullables;
import com.speedment.common.tuple.nullable.Tuple2OfNullables;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.trait.HasDefaultBuild;
import com.speedment.runtime.join.trait.HasJoins;
import com.speedment.runtime.join.trait.HasOnPredicates;
import com.speedment.runtime.join.trait.HasWhere;

import java.util.function.BiFunction;

/**
 * Join Builder stage used when only 2 tables has been specified so far.
 *
 * @param <T0>  the first entity type
 * @param <T1>  the second entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since  3.1.1
 */
public interface JoinBuilder2<T0, T1>
    extends HasJoins<JoinBuilder2.AfterJoin<T0, T1, ?>, JoinBuilder3<T0, T1, ?>>,
            HasWhere<T1, JoinBuilder2<T0, T1>>,
            HasDefaultBuild<Tuple2OfNullables<T0, T1>> {

    @Override
    <T2> AfterJoin<T0, T1, T2> innerJoinOn(HasComparableOperators<T2, ?> joinedField);

    @Override
    <T2> AfterJoin<T0, T1, T2> leftJoinOn(HasComparableOperators<T2, ?> joinedField);

    @Override
    <T2> AfterJoin<T0, T1, T2> rightJoinOn(HasComparableOperators<T2, ?> joinedField);

    @Override
    <T2> JoinBuilder3<T0, T1, T2> crossJoin(TableIdentifier<T2> joinedTable);

    @Override
    default Join<Tuple2OfNullables<T0, T1>> build() {
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
    <T> Join<T> build(BiFunction<T0, T1, T> constructor);

    interface AfterJoin<T0, T1, T2>
    extends HasOnPredicates<JoinBuilder3<T0, T1, T2>> {}
}
