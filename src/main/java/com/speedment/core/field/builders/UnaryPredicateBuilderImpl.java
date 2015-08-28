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
package com.speedment.core.field.builders;

import com.speedment.core.field.ReferenceFieldImpl;
import com.speedment.api.field.operators.StandardUnaryOperator;
import com.speedment.api.field.builders.UnaryPredicateBuilder;
import com.speedment.api.field.operators.UnaryOperator;

import java.util.Objects;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 * @param <ENTITY> Entity type
 * @param <V> Value type
 */
public class UnaryPredicateBuilderImpl<ENTITY, V> 
    extends AbstractBasePredicate<ENTITY> 
    implements UnaryPredicateBuilder<ENTITY> {

    private final ReferenceFieldImpl<ENTITY, V> field;
    private final UnaryOperator operator;

    public UnaryPredicateBuilderImpl(
        final ReferenceFieldImpl<ENTITY, V> field,
        final UnaryOperator operator
    ) {
        this.field    = requireNonNull(field);
        this.operator = requireNonNull(operator);
    }

    @Override
    public boolean test(ENTITY entity) {
        return operator.getUnaryFilter().test(field.get(entity));
    }

    @Override
    public ReferenceFieldImpl<ENTITY, V> getField() {
        return field;
    }

    @Override
    public UnaryOperator getUnaryOperator() {
        return operator;
    }
}