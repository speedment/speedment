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
package com.speedment.internal.core.field.predicate.impl;

import com.speedment.field.predicate.PredicateType;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.methods.Getter;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.core.field.predicate.AbstractBasePredicate;
import com.speedment.internal.util.Cast;
import com.speedment.internal.core.field.predicate.iface.type.HasFirstOperand;
import com.speedment.internal.core.field.predicate.iface.type.HasSecondOperand;
import com.speedment.internal.core.field.predicate.iface.type.HasThirdOperand;

/**
 *
 * @author pemi
 * @param <ENTITY> entity type
 * @param <V> value type
 */
public abstract class SpeedmentPredicateImpl<ENTITY, D, V> extends AbstractBasePredicate<ENTITY> implements SpeedmentPredicate<ENTITY, D, V> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, D, V> referenceField;
    private final PredicateType predicateType;

    protected SpeedmentPredicateImpl(
            PredicateType predicateType, 
            FieldTrait field, 
            ReferenceFieldTrait<ENTITY, D, V> referenceField
    ) {
        this.predicateType = predicateType;
        this.field = field;
        this.referenceField = referenceField;
    }

    @Override
    public final PredicateType getPredicateType() {
        return predicateType;
    }

    @Override
    public final PredicateType getEffectivePredicateType() {
        return predicateType.effectiveType(isNegated());
    }

    @Override
    public final Getter<ENTITY, V> getter() {
        return referenceField.getter();
    }

    public abstract boolean testField(V fieldValue);

    @Override
    public final boolean test(ENTITY t) {
        return testField(getter().apply(t)) ^ isNegated();
    }

    @Override
    public FieldTrait getField() {
        return field;
    }

    @Override
    public ReferenceFieldTrait<ENTITY, D, V> getReferenceField() {
        return referenceField;
    }

    @Override
    public SpeedmentPredicateImpl<ENTITY, D, V> negate() {
        super.negate();
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName())
            .append(" {")
            .append("field: ").append(field)
            .append(", type: '").append(predicateType).append("'");

        Cast.cast(this, HasFirstOperand.class).map(HasFirstOperand<?>::getFirstOperand).ifPresent(o -> sb.append(", operand0: '").append(o).append("'"));
        Cast.cast(this, HasSecondOperand.class).map(HasSecondOperand<?>::getSecondOperand).ifPresent(o -> sb.append(", operand1: '").append(o).append("'"));
        Cast.cast(this, HasThirdOperand.class).map(HasThirdOperand<?>::getThirdOperand).ifPresent(o -> sb.append(", operand2: '").append(o).append("'"));

        sb.append(", negated: ").append(isNegated())
            .append("}");
        return sb.toString();
    }

}
