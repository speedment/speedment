/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.component;

import com.speedment.annotation.Api;
import com.speedment.exception.SpeedmentException;
import com.speedment.field.ComparableField;
import com.speedment.stream.StreamDecorator;
import java.util.stream.Stream;

/**
 * This Component interface is used to obtain streams for different tables.
 *
 * @author pemi
 * @since 2.2
 */
@Api(version = "2.2")
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

    default <ENTITY, V extends Comparable<? super V>, FK>
            ENTITY find(Class<ENTITY> entityClass, ComparableField<ENTITY, V> field, V value) {
        return stream(entityClass, StreamDecorator.IDENTITY)
                .filter(field.equal(value))
                .findAny()
                .orElseThrow(() -> new SpeedmentException(
                        "Foreign key constraint error. " + entityClass.getSimpleName() + " is set to " + value)
                );
    }

}
