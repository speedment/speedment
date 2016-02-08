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

import static com.speedment.field.predicate.PredicateType.NOT_EQUAL;
import java.util.Objects;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.core.field.predicate.iface.type.BinaryOperation;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 * @param <V> value type
 */
public class NotEqualPredicate<ENTITY, V extends Comparable<? super V>> extends BaseComparablePredicate<ENTITY, V> implements SpeedmentPredicate<ENTITY, V>, BinaryOperation<V> {

    public NotEqualPredicate(FieldTrait field, ReferenceFieldTrait<ENTITY, V> referenceField, V operand0) {
        super(NOT_EQUAL, field, referenceField, operand0, (a, b) -> !Objects.equals(a, b));
    }

}
