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
package com.speedment.runtime.field.internal.expression;

import com.speedment.runtime.compute.ToString;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToString;

import java.util.function.Function;

/**
 * Default implementation of {@link FieldToString}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToStringImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, String, ToString<ENTITY>, Function<V, String>>
implements FieldToString<ENTITY, V> {

    public FieldToStringImpl(ReferenceField<ENTITY, ?, V> field,
                             Function<V, String> mapper) {
        super(field, mapper);
    }

    @Override
    public String apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.apply(value);
    }
}