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

import com.speedment.field.ReferenceComparableForeignKeyStringField;
import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 * @param <FK> the foreign entity type
 */
public class ReferenceComparableForeignKeyStringFieldImpl<ENTITY, FK>
    extends ReferenceComparableStringFieldImpl<ENTITY>
    implements ReferenceComparableForeignKeyStringField<ENTITY, FK> {

    private final Getter<ENTITY, FK> finder;

    public ReferenceComparableForeignKeyStringFieldImpl(String columnName, Getter<ENTITY, String> getter, Setter<ENTITY, String> setter, Getter<ENTITY, FK> finder) {
        super(columnName, getter, setter);
        this.finder = requireNonNull(finder);
    }

    @Override
    public Getter<ENTITY, FK> finder() {
        return finder;
    }

    @Override
    public FK findFrom(ENTITY entity) {
        requireNonNull(entity);
        return finder.apply(entity);
    }
}
