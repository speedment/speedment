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
package com.speedment.internal.core.field.builders;

import com.speedment.field.ReferenceComparableStringField;
import java.util.Objects;
import com.speedment.field.builders.StringPredicateBuilder;
import com.speedment.field.operators.StringOperator;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 * @param <ENTITY> Entity type
 *
 */
public final class StringPredicateBuilderImpl<ENTITY>
    extends AbstractBasePredicate<ENTITY>
    implements StringPredicateBuilder<ENTITY> {

    private final ReferenceComparableStringField<ENTITY> field;
    private final String value;
    private final StringOperator operator;

    public StringPredicateBuilderImpl(
        final ReferenceComparableStringField<ENTITY> field,
        final String value,
        final StringOperator operator
    ) {
        this.field = requireNonNull(field);
        this.value = value;
        this.operator = Objects.requireNonNull(operator);
    }

    @Override
    public boolean test(ENTITY entity) {
        requireNonNull(entity);
        final String columnValue = field.get(entity);

        if (columnValue == null || value == null) {
            return false;
        } else {
            return operator.getStringFilter().test(columnValue, value);
        }
    }

    @Override
    public ReferenceComparableStringField<ENTITY> getField() {
        return field;
    }

    @Override
    public String getOperand() {
        return value;
    }

    @Override
    public StringOperator getStringOperator() {
        return operator;
    }

}
