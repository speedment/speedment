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
package com.speedment.internal.core.field2.predicate.impl;

import com.speedment.field2.predicate.PredicateType;
import com.speedment.field2.predicate.SpeedmentPredicate;
import com.speedment.field2.methods.Getter;
import com.speedment.field2.trait.FieldTrait;
import com.speedment.internal.core.field2.predicate.AbstractBasePredicate;
import com.speedment.internal.core.field2.predicate.iface.type.HasOperand0;
import com.speedment.internal.core.field2.predicate.iface.type.HasOperand1;
import com.speedment.internal.core.field2.predicate.iface.type.HasOperand2;
import com.speedment.internal.util.Cast;

/**
 *
 * @author pemi
 * @param <ENTITY> entity type
 * @param <V> value type
 */
public abstract class SpeedmentPredicateImpl<ENTITY, V> extends AbstractBasePredicate<ENTITY> implements SpeedmentPredicate<ENTITY, V> {

    private final FieldTrait field;
    private final PredicateType predicateType;
    private final Getter<ENTITY, V> getter;

    protected SpeedmentPredicateImpl(PredicateType predicateType, FieldTrait field, Getter<ENTITY, V> getter/*, Predicate<V> fieldPredicate*/) {
        this.predicateType = predicateType;
        this.field = field;
        this.getter = getter;
    }

    @Override
    public final PredicateType getPredicateType() {
        return predicateType;
    }

    @Override
    public final Getter<ENTITY, V> getter() {
        return getter;
    }

    public abstract boolean testField(V fieldValue);

    @Override
    public final boolean test(ENTITY t) {
        return testField(getter.apply(t)) ^ isNegated();
    }

    @Override
    public FieldTrait getField() {
        return field;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName())
            .append(" {")
            .append("field: ").append(field)
            .append(", type: '").append(predicateType);

        Cast.cast(this, HasOperand0.class).map(HasOperand0<?>::getOperand0).ifPresent(o -> sb.append(", operand0: '").append(o));
        Cast.cast(this, HasOperand1.class).map(HasOperand1<?>::getOperand1).ifPresent(o -> sb.append(", operand1: '").append(o));
        Cast.cast(this, HasOperand2.class).map(HasOperand2<?>::getOperand2).ifPresent(o -> sb.append(", operand2: '").append(o));
        
        sb.append("', negated: ").append(isNegated())
            .append("}");
        return sb.toString();
    }

}
