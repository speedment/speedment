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
package com.speedment.runtime.internal.core.field.predicate.impl.comparable;

import com.speedment.runtime.field.Inclusion;
import com.speedment.runtime.field.predicate.ComparableSpeedmentPredicate;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_BETWEEN;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.internal.core.field.predicate.iface.type.QuaternaryInclusionOperation;
import com.speedment.runtime.internal.core.field.predicate.impl.SpeedmentPredicateImpl;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 * @param <V> value type
 */
public class NotBetweenPredicate<ENTITY, D, V extends Comparable<? super V>>
    extends SpeedmentPredicateImpl<ENTITY, D, V>
    implements SpeedmentPredicate<ENTITY, D, V>, QuaternaryInclusionOperation<V, V>, ComparableSpeedmentPredicate<ENTITY, D, V> {

    private final V operand0;
    private final V operand1;
    private final Inclusion operand2;

    public NotBetweenPredicate(
        FieldTrait field,
        ReferenceFieldTrait<ENTITY, D, V> referenceField,
        V start,
        V end,
        Inclusion inclusion
    ) {
        super(NOT_BETWEEN, field, referenceField);
        this.operand0 = start;
        this.operand1 = end;
        this.operand2 = inclusion;
    }

    @Override
    public V getFirstOperand() {
        return operand0;
    }

    @Override
    public V getSecondOperand() {
        return operand1;
    }

    @Override
    public Inclusion getThirdOperand() {
        return operand2;
    }

    @Override
    public boolean testField(V fieldValue) {
        switch (operand2) {
            case START_EXCLUSIVE_END_EXCLUSIVE: {
                return !(operand0.compareTo(fieldValue) < 0 && operand1.compareTo(fieldValue) > 0);
            }
            case START_EXCLUSIVE_END_INCLUSIVE: {
                return !(operand0.compareTo(fieldValue) < 0 && operand1.compareTo(fieldValue) >= 0);
            }
            case START_INCLUSIVE_END_EXCLUSIVE: {
                return !(operand0.compareTo(fieldValue) <= 0 && operand1.compareTo(fieldValue) > 0);
            }
            case START_INCLUSIVE_END_INCLUSIVE: {
                return !(operand0.compareTo(fieldValue) <= 0 && operand1.compareTo(fieldValue) >= 0);
            }
        }
        throw new IllegalStateException("Inclusion unknown: " + operand2);
    }

}
