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
package com.speedment.internal.core.field2.predicate.impl.comparable;

import com.speedment.field2.Inclusion;
import static com.speedment.internal.core.field2.predicate.PredicateType.BETWEEN;
import com.speedment.field2.methods.Getter;
import com.speedment.internal.core.field2.predicate.iface.SpeedmentPredicate;
import com.speedment.internal.core.field2.predicate.iface.type.QuaternaryInclusionOperation;
import com.speedment.internal.core.field2.predicate.impl.SpeedmentPredicateImpl;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 * @param <V> value type
 */
public class BetweenPredicate<ENTITY, V extends Comparable<? super V>>
    extends SpeedmentPredicateImpl<ENTITY, V>
    implements SpeedmentPredicate<ENTITY, V>, QuaternaryInclusionOperation<V, V> {

    private final V operand0;
    private final V operand1;
    private final Inclusion operand2;

    public BetweenPredicate(Getter<ENTITY, V> getter, V start, V end, Inclusion inclusion) {
        super(BETWEEN, getter);
        this.operand0 = start;
        this.operand1 = end;
        this.operand2 = inclusion;
    }

    @Override
    public V getOperand0() {
        return operand0;
    }

    @Override
    public V getOperand1() {
        return operand1;
    }

    @Override
    public Inclusion getOperand2() {
        return operand2;
    }

    @Override
    public boolean testField(V fieldValue) {
        switch (operand2) {
            case START_EXCLUSIVE_END_EXCLUSIVE: {
                return (operand0.compareTo(fieldValue) < 0 && operand1.compareTo(fieldValue) > 0);
            }
            case START_EXCLUSIVE_END_INCLUSIVE: {
                return (operand0.compareTo(fieldValue) < 0 && operand1.compareTo(fieldValue) >= 0);
            }
            case START_INCLUSIVE_END_EXCLUSIVE: {
                return (operand0.compareTo(fieldValue) <= 0 && operand1.compareTo(fieldValue) > 0);
            }
            case START_INCLUSIVE_END_INCLUSIVE: {
                return (operand0.compareTo(fieldValue) <= 0 && operand1.compareTo(fieldValue) >= 0);
            }
        }
        throw new IllegalStateException("Inclusion unknown: " + operand2);
    }

}
