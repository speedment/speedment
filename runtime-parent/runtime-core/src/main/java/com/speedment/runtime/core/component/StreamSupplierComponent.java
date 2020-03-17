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
package com.speedment.runtime.core.component;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.trait.HasComparableOperators;

import java.util.Optional;
import java.util.stream.Stream;

import static com.speedment.runtime.core.stream.parallel.ParallelStrategy.computeIntensityDefault;

/**
 * This Component interface is used to obtain streams for different tables.
 *
 * @author  Per Minborg
 * @since   2.2.0
 */
@InjectKey(StreamSupplierComponent.class)
public interface StreamSupplierComponent {

    /**
     * Basic stream over all entities using the default
     * {@link ParallelStrategy}.
     *
     * @param <ENTITY>  entity type
     * @param tableId   the identifier to use
     * @return          a stream for the given entity class
     *
     * @since 3.0.15
     */
    default <ENTITY> Stream<ENTITY> stream(TableIdentifier<ENTITY> tableId) {
        return stream(tableId, computeIntensityDefault());
    }
    
    /**
     * Basic stream over all entities.
     *
     * @param <ENTITY>        entity type
     * @param tableIdentifier the identifier to use
     * @param strategy        decorates the stream before building it
     * @return                a stream for the given entity class
     */
    <ENTITY> Stream<ENTITY> stream(TableIdentifier<ENTITY> tableIdentifier, ParallelStrategy strategy);

    /**
     * Finds a particular entity in the source where the specified field has 
     * the specified value. This is a form of key-value lookup than can 
     * potentially be more efficient with for an example foreign key references.
     * 
     * @param <ENTITY>        the entity type
     * @param <V>             the java type of the column
     * @param tableIdentifier the identifier to use
     * @param field           the field to select on
     * @param value           the value of that field for the entity to return
     * @return                entity found or empty if none existed with that value
     */
    default <ENTITY, V extends Comparable<? super V>> 
    Optional<ENTITY> findAny(
            TableIdentifier<ENTITY>  tableIdentifier, 
            HasComparableOperators<ENTITY, V> field, 
            V value) {
        
        return stream(tableIdentifier, ParallelStrategy.computeIntensityDefault())
            .filter(field.equal(value))
            .findAny();
    }

    /**
     * Returns if this stream component will return the same stream result over
     * time (immutable or analytics type of data).
     *
     * @return  {@code true} if the source is immutable
     */
    default boolean isImmutable() {
        return false;
    }
    
    /**
     * Starts the stream suppler and initialized any resources needed for its
     * operation.
     */
    default void start(){}

    /**
     * Stops the stream suppler and releases any previously allocated resources.
     */
    default void stop(){}

    /**
     * Creates and returns a new Stream of underlying {@code StreamSupplierComponent} objects
     * that provides data to this {@code StreamSupplierComponent}. If no such component exists
     * (i.e. the component draws data only from external sources like databases or files) an
     * empty stream is returned.
     *
     * @return a new Stream of underlying {@code StreamSupplierComponent} objects
     * that provides data to this {@code StreamSupplierComponent}
     */
    default Stream<StreamSupplierComponent> sourceStreamSupplierComponents() {
        return Stream.empty();
    }

}