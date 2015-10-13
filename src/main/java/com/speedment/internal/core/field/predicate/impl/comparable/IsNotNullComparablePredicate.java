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
package com.speedment.internal.core.field.predicate.impl.comparable;

import com.speedment.field.methods.Getter;
import static com.speedment.field.predicate.PredicateType.IS_NOT_NULL;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.internal.core.field.predicate.iface.type.BinaryOperation;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 * @param <V> value type
 */
public class IsNotNullComparablePredicate<ENTITY, V extends Comparable<? super V>> extends BaseComparablePredicate<ENTITY, V> implements SpeedmentPredicate<ENTITY, V>, BinaryOperation<V> {

    public IsNotNullComparablePredicate(FieldTrait field, Getter<ENTITY, V> getter) {
        super(IS_NOT_NULL, field, getter, null, (v, f) -> f != null);
    }
}
