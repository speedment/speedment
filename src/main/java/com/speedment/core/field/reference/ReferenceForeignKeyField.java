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
package com.speedment.core.field.reference;

import com.speedment.core.config.model.Column;

import java.util.function.Supplier;

/**
 * This class represents a Reference Field that is a Foreign key to another
 * table/column. A Reference Field is something that extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 * @param <FK> The foreign entity type
 */
public class ReferenceForeignKeyField<ENTITY, V, FK> extends ReferenceField<ENTITY, V> {

    private final Getter<ENTITY, FK> finder;

    public ReferenceForeignKeyField(Supplier<Column> columnSupplier, Getter<ENTITY, V> getter, Setter<ENTITY, V> setter, Getter<ENTITY, FK> finder) {
        super(columnSupplier, getter, setter);
        this.finder = finder;
    }

    /**
     * Finds and returns the foreign key Entity using the provided Entity.
     *
     * @param entity to use when finding the foreign key Entity
     * @return the foreign key Entity
     */
    public FK findFrom(ENTITY entity) {
        return finder.apply(entity);
    }
}
