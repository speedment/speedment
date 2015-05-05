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
package com.speedment.orm.field.ints;

import com.speedment.orm.field.BasePredicate;
import com.speedment.orm.field.BinaryPredicateBuilder;
import com.speedment.orm.field.StandardBinaryOperator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 * @param <ENTITY> Entity type
 */
public class IntBinaryPredicateBuilder<ENTITY> extends BasePredicate<ENTITY> implements Predicate<ENTITY>, BinaryPredicateBuilder<ENTITY, Integer> {

    private final IntField<ENTITY> field;
    private final int value;
    private final StandardBinaryOperator binaryOperator;

    public IntBinaryPredicateBuilder(
        final IntField<ENTITY> field,
        final int value,
        final StandardBinaryOperator binaryOperator
    ) {
        this.field = Objects.requireNonNull(field);
        this.value = value;
        this.binaryOperator = Objects.requireNonNull(binaryOperator);
    }

    @Override
    public boolean test(ENTITY entity) {
        return test(Integer.compare(field.getFrom(entity), getValue()));
    }

    public boolean test(int compare) {
        return binaryOperator.getComparator().test(compare);
    }

    @Override
    public IntField<ENTITY> getField() {
        return field;
    }

    @Override
    public StandardBinaryOperator getOperator() {
        return binaryOperator;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Integer getValueAsObject() {
        return getValue();
    }

}
