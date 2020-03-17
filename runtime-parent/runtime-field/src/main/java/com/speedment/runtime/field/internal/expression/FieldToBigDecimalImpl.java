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

import com.speedment.runtime.compute.ToBigDecimal;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToBigDecimal;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Default implementation of {@link FieldToBigDecimal}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToBigDecimalImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, BigDecimal, ToBigDecimal<ENTITY>, Function<V, BigDecimal>>
implements FieldToBigDecimal<ENTITY, V> {

    public FieldToBigDecimalImpl(ReferenceField<ENTITY, ?, V> field,
                                 Function<V, BigDecimal> mapper) {
        super(field, mapper);
    }

    @Override
    public BigDecimal apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.apply(value);
    }
}