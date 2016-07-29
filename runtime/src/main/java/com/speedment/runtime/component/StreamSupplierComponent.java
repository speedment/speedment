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
package com.speedment.runtime.component;

import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.stream.StreamDecorator;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * This Component interface is used to obtain streams for different tables.
 *
 * @author  Per Minborg
 * @since   2.2.0
 */
@Api(version = "3.0")
@InjectorKey(StreamSupplierComponent.class)
public interface StreamSupplierComponent extends Component {
    
    /**
     * Does all the preparations required to start serving streams 
     * before returning. The component must never be called before
     * this method has returned.
     */
    void start();
    
    /**
     * Stops the componenet, releasing any resources. When this method
     * returns, the component must never be called again.
     */
    void stop();

    /**
     * Basic stream over all entities.
     *
     * @param <ENTITY>     entity type
     * @param entityClass  the entity class
     * @param decorator    decorates the stream before building it
     * @return             a stream for the given entity class
     */
    <ENTITY> Stream<ENTITY> stream(Class<ENTITY> entityClass, StreamDecorator decorator);

    /**
     * Finds a particular entity in the source where the specified field has 
     * the specified value. This is a form of key-value lookup than can 
     * potentially be more efficient with for an example foreign key references.
     * 
     * @param <ENTITY>     the entity type
     * @param <V>          the java type of the column
     * @param entityClass  the entity interface .class
     * @param field        the field to select on
     * @param value        the value of that field for the entity to return
     * @return             entity found or empty if none existed with that value
     */
    default <ENTITY, V extends Comparable<? super V>> 
    Optional<ENTITY> findAny(
            Class<ENTITY> entityClass, 
            HasComparableOperators<ENTITY, V> field, 
            V value) {
        
        return stream(entityClass, StreamDecorator.IDENTITY)
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
    
    @Override
    default Class<StreamSupplierComponent> getComponentClass() {
        return StreamSupplierComponent.class;
    }
}