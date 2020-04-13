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
package com.speedment.runtime.field.method;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.Field;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A handle for a find-operation that can be replaced runtime to optimize a 
 * {@code Stream}. Formally, a {@code FindFrom} has the following signature:
 * {@code
 *      FK_ENTITY apply(ENTITY entity);
 * }
 * <p>
 * Each {@code FindFrom} contains metadata of which fields it compares to 
 * determine the mapping as well as a reference to the foreign manager.
 * <p>
 * {@code FindFromNullable} is a specialization of {@link FindFrom} that is used
 * to signal that the referenced entity might not exist.
 * 
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * 
 * @author Emil Forslund
 * @since  3.0.10
 */
public interface FindFromNullable<ENTITY, FK_ENTITY>
extends Function<ENTITY, Stream<FK_ENTITY>> {

    /**
     * Returns the identifier for the referenced (foreign) table.
     *
     * @return  target (foreign) table identifier
     */
    TableIdentifier<FK_ENTITY> getTableIdentifier();
    
    /**
     * Returns the field that the stream originates from. 
     * <p>
     * In the following example, {@code foo} is the source:
     * <pre>{@code
     *      foos.stream()
     *          .flatMap(foo.findBars()) // findBars returns FindFromNullable<Foo, Bar>
     *          .forEach(...);
     * }</pre>
     * 
     * @return  the source field
     */
    Field<ENTITY> getSourceField();
    
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
    Field<FK_ENTITY> getTargetField();

    /**
     * Returns {@code true} if the {@link #apply(Object)}-method will return a
     * stream with a value. Otherwise, {@code false} is returned.
     *
     * @param entity  the entity to locate a foreign entity for
     * @return        singleton or empty stream
     */
    boolean isPresent(ENTITY entity);

    /**
     * Applies this method, returning the referenced entity. If no entity was
     * referenced, then an exception is thrown. This method should therefore not
     * be invoked unless the status of the availability of the entity has been
     * checked with {@link #isPresent(Object)}.
     * <p>
     * Another way of retrieving the referenced entity is to use the
     * {@link #apply(Object)}-method.
     *
     * @param entity  the entity to locate a foreign entity for
     * @return        the referenced foreign entity
     *
     * @throws IllegalArgumentException  if no foreign entity was referenced
     */
    FK_ENTITY applyOrThrow(ENTITY entity);

    /**
     * Applies this method, locating the referenced entity and returning it as a
     * singleton stream if found. If no entity was referenced, an empty stream
     * will be returned.
     *
     * @param entity  the entity to locate a foreign entity for
     * @return        singleton or empty stream
     */
    @Override
    Stream<FK_ENTITY> apply(ENTITY entity);
}
