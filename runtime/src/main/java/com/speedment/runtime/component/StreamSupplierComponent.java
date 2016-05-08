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

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.ComparableFieldTrait;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.stream.StreamDecorator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This Component interface is used to obtain streams for different tables.
 *
 * @author pemi
 * @since 2.2
 */
@Api(version = "2.3")
public interface StreamSupplierComponent extends Component {

    @Override
    default Class<StreamSupplierComponent> getComponentClass() {
        return StreamSupplierComponent.class;
    }

    /**
     * Basic stream over all entities.
     *
     * @param <ENTITY> entity type
     * @param entityClass the entity class
     * @param decorator decorates the stream before building it
     * @return a stream for the given entity class
     */
    <ENTITY> Stream<ENTITY> stream(Class<ENTITY> entityClass, StreamDecorator decorator);

    default <ENTITY, D, V extends Comparable<? super V>, F extends FieldTrait & ReferenceFieldTrait<ENTITY, D, V> & ComparableFieldTrait<ENTITY, D, V>>
            Optional<ENTITY> findAny(Class<ENTITY> entityClass, F field, V value) {
        return stream(entityClass, StreamDecorator.IDENTITY)
                .filter(field.equal(value))
                .findAny();
    }

    /**
     * Returns if this stream component will return the same stream result over
     * time (immutable or analytics type of data).
     *
     * @return if this stream component will return the same stream result over
     * time.
     */
    default boolean isImmutable() {
        return false;
    }

}
