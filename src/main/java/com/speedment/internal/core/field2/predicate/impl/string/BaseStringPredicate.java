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
package com.speedment.internal.core.field2.predicate.impl.string;

import com.speedment.internal.core.field2.predicate.PredicateType;
import com.speedment.field2.methods.Getter;
import com.speedment.internal.core.field2.predicate.iface.SpeedmentPredicate;
import com.speedment.internal.core.field2.predicate.impl.SpeedmentPredicateImpl;
import com.speedment.internal.core.field2.predicate.iface.type.BinaryOperation;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public class BaseStringPredicate<ENTITY> extends SpeedmentPredicateImpl<ENTITY, String>
    implements SpeedmentPredicate<ENTITY, String>, BinaryOperation<String> {

    private final String operand0;
    private final BiStringPredicate innerPredicate;

    public BaseStringPredicate(PredicateType predicateType,
        Getter<ENTITY, String> getter,
        String operand0,
        BiStringPredicate innerPredicate
    ) {
        super(predicateType, getter);
        this.operand0 = operand0;
        this.innerPredicate = innerPredicate;
    }

    @Override
    public String getOperand0() {
        return operand0;
    }

    @Override
    public boolean testField(String fieldValue) {
        return innerPredicate.test(fieldValue, operand0);
    }

    @FunctionalInterface
    public interface BiStringPredicate {

        boolean test(String s0, String s1);
    }

    public static final BiStringPredicate EQUALS_IGNORE_CASE_PREDICATE
        = (s0, s1) -> (s0 == s1) || (s0 != null && s0.equalsIgnoreCase(s1));

    public static final BiStringPredicate NOT_EQUALS_IGNORE_CASE_PREDICATE
        = (s0, s1) -> !EQUALS_IGNORE_CASE_PREDICATE.test(s0, s1);

    public static final BiStringPredicate STARTS_WITH_PREDICATE
        = (s0, s1) ->(s0 != null && s1 != null) && (s0.startsWith(s1));

    public static final BiStringPredicate ENDS_WITH_PREDICATE
        = (s0, s1) -> (s0 != null && s1 != null) && (s0.endsWith(s1));

    public static final BiStringPredicate CONTAINS_PREDICATE
        = (s0, s1) -> (s0 != null && s1 != null) && (s0.contains(s1));

}
