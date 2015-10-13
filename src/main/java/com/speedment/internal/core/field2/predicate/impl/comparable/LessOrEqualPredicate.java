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

import static com.speedment.field2.predicate.PredicateType.LESS_OR_EQUAL;
import com.speedment.field2.methods.Getter;
import com.speedment.field2.predicate.SpeedmentPredicate;
import com.speedment.field2.trait.FieldTrait;
import com.speedment.internal.core.field2.predicate.iface.type.BinaryOperation;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 * @param <V> value type
 */
public class LessOrEqualPredicate<ENTITY, V extends Comparable<? super V>>
    extends BaseComparablePredicate<ENTITY, V>
    implements SpeedmentPredicate<ENTITY, V>, BinaryOperation<V> {

    public LessOrEqualPredicate(FieldTrait field, Getter<ENTITY, V> getter, V operand0) {
        super(LESS_OR_EQUAL, field, getter, operand0, LessOrEqualPredicate::test);
    }

    private static <V extends Comparable<? super V>> boolean test(V a, V b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.compareTo(b) >= 0;
    }

}
