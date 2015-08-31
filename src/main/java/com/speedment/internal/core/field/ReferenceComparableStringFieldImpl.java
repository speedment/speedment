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

import com.speedment.field.ReferenceComparableStringField;
import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import com.speedment.field.operators.StandardStringOperator;
import com.speedment.internal.core.field.builders.StringPredicateBuilderImpl;
import com.speedment.field.builders.StringPredicateBuilder;

/**
 *
 * @author pemi
 * @param <ENTITY>  the entity type
 */
public class ReferenceComparableStringFieldImpl<ENTITY>
    extends ReferenceComparableFieldImpl<ENTITY, String> 
    implements ReferenceComparableStringField<ENTITY> {

    public ReferenceComparableStringFieldImpl(String columnName, Getter<ENTITY, String> getter, Setter<ENTITY, String> setter) {
        super(columnName, getter, setter);
    }

    @Override
    public StringPredicateBuilder<ENTITY> equalIgnoreCase(String value) {
        return newBinary(value, StandardStringOperator.EQUAL_IGNORE_CASE);
    }

    @Override
    public StringPredicateBuilder<ENTITY> notEqualIgnoreCase(String value) {
        return newBinary(value, StandardStringOperator.NOT_EQUAL_IGNORE_CASE);
    }

    @Override
    public StringPredicateBuilder<ENTITY> startsWith(String value) {
        return newBinary(value, StandardStringOperator.STARTS_WITH);
    }

    @Override
    public StringPredicateBuilder<ENTITY> endsWith(String value) {
        return newBinary(value, StandardStringOperator.ENDS_WITH);
    }

    @Override
    public StringPredicateBuilder<ENTITY> contains(String value) {
        return newBinary(value, StandardStringOperator.CONTAINS);
    }

    protected StringPredicateBuilder<ENTITY> newBinary(String value, StandardStringOperator binaryOperator) {
        return new StringPredicateBuilderImpl<>(this, value, binaryOperator);
    }
}