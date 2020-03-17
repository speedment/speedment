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

import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToLong;

import java.util.function.ToLongFunction;

/**
 * Default implementation of {@link FieldToLong}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToLongImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Long, ToLong<ENTITY>, ToLongFunction<V>>
implements FieldToLong<ENTITY, V> {

    public FieldToLongImpl(ReferenceField<ENTITY, ?, V> field,
                           ToLongFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Long apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsLong(value);
    }

    @Override
    public long applyAsLong(ENTITY object) {
        return mapper.applyAsLong(field.get(object));
    }
}