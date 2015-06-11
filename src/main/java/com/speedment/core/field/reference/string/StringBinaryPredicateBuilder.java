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
package com.speedment.core.field.reference.string;

import com.speedment.core.field.BasePredicate;
import com.speedment.core.field.BinaryPredicateBuilder;
import com.speedment.core.field.StandardStringBinaryOperator;
import java.util.Objects;

/**
 *
 * @author pemi
 * @param <ENTITY> Entity type
 *
 */
public class StringBinaryPredicateBuilder<ENTITY> extends BasePredicate<ENTITY> implements BinaryPredicateBuilder<ENTITY, String> {

    private final StringReferenceField<ENTITY> field;
    private final String value;
    private final StandardStringBinaryOperator binaryOperator;

    public StringBinaryPredicateBuilder(
        final StringReferenceField<ENTITY> field,
        final String value,
        final StandardStringBinaryOperator binaryOperator
    ) {
        this.field = Objects.requireNonNull(field);
        this.value = value;
        this.binaryOperator = Objects.requireNonNull(binaryOperator);
    }

    @Override
    public boolean test(ENTITY entity) {
        final String columnValue = field.getFrom(entity);
        if (columnValue == null || value == null) {
            return false;
        }
        return binaryOperator.getComparator().test(columnValue, value);
    }

    @Override
    public StringReferenceField<ENTITY> getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    @Override
    public StandardStringBinaryOperator getOperator() {
        return binaryOperator;
    }

    @Override
    public String getValueAsObject() {
        return getValue();
    }

}
