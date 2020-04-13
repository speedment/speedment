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
package com.speedment.runtime.core.internal.component.sql;

import com.speedment.runtime.core.component.sql.SqlStreamSupplierComponent;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.trait.HasComparableOperators;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * The common interface for table specific stream handlers that is managed by a 
 * {@link SqlStreamSupplierComponent}.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
interface SqlStreamSupplier<ENTITY> {

    /**
     * Produces a stream over the entities in the managed table. The 
     * implementation of this method is free to produce a mutable stream that 
     * can be modified before terminating, for an example by converting parts or
     * the whole stream into a SQL query.
     * 
     * @param parallelStrategy  the parallel strategy to use
     * @return                  the entity stream
     */
    Stream<ENTITY> stream(ParallelStrategy parallelStrategy);
    
    /**
     * Finds a particular entity based on an ordinary key-value search. This is
     * potentially faster than using the 
     * {@link #stream(ParallelStrategy)}-method.
     * <p>
     * If multiple entities exist with the specified value for the specified 
     * field, any one of them might be returned. Which one is not defined.
     * 
     * @param <V>    the value type
     * @param field  the field to select by
     * @param value  the value to look for
     * @return       one entity that matches the search or empty
     */
    <V extends Comparable<? super V>> Optional<ENTITY> findAny(
        HasComparableOperators<ENTITY, V> field, V value);
}