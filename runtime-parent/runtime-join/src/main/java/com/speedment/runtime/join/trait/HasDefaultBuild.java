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

import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.TupleOfNullables;
import com.speedment.runtime.join.Join;

/**
 *
 * @author Per Minborg
 * @param <R> RETURN TYPE
 *
 */
public interface HasDefaultBuild<R extends TupleOfNullables> {

    /**
     * Creates and returns a new Join object where elements in the Join object's
     * stream method is of a default {@link Tuple} type.
     *
     * @return a new Join object where elements in the Join object's stream
     * method is of a default {@link Tuple} type
     *
     * @throws IllegalStateException if fields that are added via the {@code on()
     * } method refers to tables that are not a part of the join.
     */
    Join<R> build();

}
