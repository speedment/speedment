/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.field.method;

import com.speedment.runtime.field.trait.HasFinder;
import com.speedment.runtime.manager.Manager;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * An operation that can produce a {@code Stream} of foreign entities given a
 * single entity. This is useful since it can be passed to an outer stream as
 * the argument for a {@code flatMap()}-operation.
 * <p>
 * Each streamer contains metadata of which fields it compares to determine the
 * mapping as well as a reference to the foreign manager.
 * <p>
 * Streamers have the following signature:
 * {@code
 *      Stream<FK_ENTITY> apply(ENTITY entity);
 * }
 * 
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * @param <V>          the wrapper type
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public interface BackwardFinder<ENTITY, FK_ENTITY, V> 
    extends Function<ENTITY, Stream<FK_ENTITY>> {
    
    /**
     * Returns the field that the stream references. 
     * <p>
     * In the following example, {@code bar} is the target:
     * {@code
     *      foos.stream()
     *          .flatMap(foo.findBars()) // findBars returns Streamer<Foo, Bar>
     *          .forEach(...);
     * }
     * 
     * @return  the target field
     */
    HasFinder<FK_ENTITY, ENTITY, V> getField();
    
    /**
     * Returns the manager used for the referenced (foreign) table.
     * 
     * @return  target (foreign) manager
     */
    Manager<FK_ENTITY> getTargetManager();
}