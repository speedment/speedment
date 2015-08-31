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
package com.speedment.internal.core.field;

import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import com.speedment.field.ReferenceForeignKeyField;
import static java.util.Objects.requireNonNull;

/**
 * This class represents a Reference Field that is a Foreign key to another
 * table/column. A Reference Field is something that extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 * @param <FK> The foreign entity type
 */
public class ReferenceForeignKeyFieldImpl<ENTITY, V, FK>
    extends ReferenceFieldImpl<ENTITY, V>
    implements ReferenceForeignKeyField<ENTITY, V, FK> {

    private final Getter<ENTITY, FK> finder;

    public ReferenceForeignKeyFieldImpl(String columnName, Getter<ENTITY, V> getter, Setter<ENTITY, V> setter, Getter<ENTITY, FK> finder) {
        super(columnName, getter, setter);
        this.finder = requireNonNull(finder);
    }

    @Override
    public FK findFrom(ENTITY entity) {
        requireNonNull(entity);
        return finder.apply(entity);
    }

    @Override
    public Getter<ENTITY, FK> finder() {
        return finder;
    }
}
