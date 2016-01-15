/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.field.predicate.impl.comparable;

import com.speedment.field.predicate.PredicateType;
import com.speedment.field.predicate.ComparableSpeedmentPredicate;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.core.field.predicate.impl.SpeedmentPredicateImpl;
import com.speedment.internal.core.field.predicate.iface.type.BinaryOperation;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 * @param <V> value type
 */
public class BaseComparablePredicate<ENTITY, V extends Comparable<? super V>> extends SpeedmentPredicateImpl<ENTITY, V> implements
    SpeedmentPredicate<ENTITY, V>, BinaryOperation<V>, ComparableSpeedmentPredicate<ENTITY, V> {

    private final V operand0;
    private final BiPredicate<V> innerPredicate;

    public BaseComparablePredicate(
        PredicateType predicateType,
        FieldTrait field,
        ReferenceFieldTrait<ENTITY, V> referenceField,
        V operand0,
        BiPredicate<V> innerPredicate
    ) {
        super(predicateType, field, referenceField);
        this.operand0 = operand0;
        this.innerPredicate = innerPredicate;
    }

    @Override
    public V getFirstOperand() {
        return operand0;
    }

    @Override
    public boolean testField(V fieldValue) {
        return innerPredicate.test(operand0, fieldValue);
    }

    @FunctionalInterface
    protected interface BiPredicate<T> {

        boolean test(T s0, T s1);
    }

}
