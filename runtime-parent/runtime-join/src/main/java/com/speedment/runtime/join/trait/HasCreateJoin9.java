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
package com.speedment.runtime.join.trait;

import com.speedment.common.function.Function9;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.stage.Stage;

import java.util.List;

/**
 *
 * @author Per Minborg
 */
public interface HasCreateJoin9 {

    /**
     * Creates and returns a new Join object using the provided {@code pipeline}
     * whereby elements in the returned Join's {@link Join#stream() } method
     * will be constructed using the provided {@code constructor}.
     *
     * @param <T0> entity type of the first table
     * @param <T1> entity type of the second table
     * @param <T2> entity type of the third table
     * @param <T3> entity type of the fourth table
     * @param <T4> entity type of the fifth table
     * @param <T5> entity type of the sixth table
     * @param <T6> entity type of the seventh table
     * @param <T7> entity type of the eighth table
     * @param <T8> entity type of the ninth table
     * @param <T> stream type in returned Join object's stream method
     * @param stages pipeline with information on the joined tables
     * @param constructor to be applied by the returned Join objects stream
     * method
     * @param t0 identifier of the first table
     * @param t1 identifier of the second table
     * @param t2 identifier of the third table
     * @param t3 identifier of the fourth table
     * @param t4 identifier of the fifths table
     * @param t5 identifier of the sixth table
     * @param t6 identifier of the seventh table
     * @param t7 identifier of the eighth table
     * @param t8 identifier of the ninth table
     * @return a new Join object
     *
     * @throws NullPointerException if any of the provided arguments are
     * {@code null}
     */
    <T0, T1, T2, T3, T4, T5, T6, T7, T8, T> Join<T> createJoin(
            List<Stage<?>> stages,
            Function9<T0, T1, T2, T3, T4, T5, T6, T7, T8, T> constructor,
            TableIdentifier<T0> t0,
            TableIdentifier<T1> t1,
            TableIdentifier<T2> t2,
            TableIdentifier<T3> t3,
            TableIdentifier<T4> t4,
            TableIdentifier<T5> t5,
            TableIdentifier<T6> t6,
            TableIdentifier<T7> t7,
            TableIdentifier<T8> t8
    );

}
