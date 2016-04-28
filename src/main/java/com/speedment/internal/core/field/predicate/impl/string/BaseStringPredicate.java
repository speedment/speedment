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
package com.speedment.internal.core.field.predicate.impl.string;

import com.speedment.field.predicate.PredicateType;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.predicate.StringSpeedmentPredicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.core.field.predicate.iface.type.BinaryOperation;
import com.speedment.internal.core.field.predicate.impl.SpeedmentPredicateImpl;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public class BaseStringPredicate<ENTITY, D> extends SpeedmentPredicateImpl<ENTITY, D, String>
    implements SpeedmentPredicate<ENTITY, D, String>, BinaryOperation<String>, StringSpeedmentPredicate<ENTITY, D> {

    private final String operand0;
    private final BiStringPredicate innerPredicate;

    public BaseStringPredicate(PredicateType predicateType,
        FieldTrait field,
        ReferenceFieldTrait<ENTITY, D, String> referenceField,
        String operand0,
        BiStringPredicate innerPredicate
    ) {
        super(predicateType, field, referenceField);
        this.operand0 = operand0;
        this.innerPredicate = innerPredicate;
    }

    @Override
    public String getFirstOperand() {
        return operand0;
    }

    @Override
    public boolean testField(String fieldValue) {
        return innerPredicate.test(operand0, fieldValue);
    }

    @FunctionalInterface
    public interface BiStringPredicate {

        boolean test(String s0, String s1);
    }

    public static final BiStringPredicate EQUALS_IGNORE_CASE_PREDICATE
        = (v, f) -> (v == f) || (f != null && f.equalsIgnoreCase(v));

    public static final BiStringPredicate NOT_EQUALS_IGNORE_CASE_PREDICATE
        = (v, f) -> !EQUALS_IGNORE_CASE_PREDICATE.test(v, f);

    public static final BiStringPredicate STARTS_WITH_PREDICATE
        = (v, f) -> (v != null && f != null) && (f.startsWith(v));

    public static final BiStringPredicate NOT_STARTS_WITH_PREDICATE
        = (v, f) -> !STARTS_WITH_PREDICATE.test(v, f);

    public static final BiStringPredicate ENDS_WITH_PREDICATE
        = (v, f) -> (v != null && f != null) && (f.endsWith(v));

    public static final BiStringPredicate NOT_ENDS_WITH_PREDICATE
        = (v, f) -> !ENDS_WITH_PREDICATE.test(v, f);

    public static final BiStringPredicate CONTAINS_PREDICATE
        = (v, f) -> (v != null && f != null) && (f.contains(v));

    public static final BiStringPredicate NOT_CONTAINS_PREDICATE
        = (v, f) -> !CONTAINS_PREDICATE.test(v, f);

}
