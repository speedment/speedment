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
package com.speedment.runtime.internal.field.predicate.impl.comparable;

import static com.speedment.runtime.field.predicate.PredicateType.IS_NOT_NULL;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.internal.field.predicate.iface.type.BinaryOperation;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 * @param <V> value type
 */
public class IsNotNullComparablePredicate<ENTITY, D, V extends Comparable<? super V>>
        extends BaseComparablePredicate<ENTITY, D, V>
        implements SpeedmentPredicate<ENTITY, D, V>, BinaryOperation<V> {

    public IsNotNullComparablePredicate(FieldTrait field, ReferenceFieldTrait<ENTITY, D, V> referenceField) {
        super(IS_NOT_NULL, field, referenceField, null, (v, f) -> f != null);
    }
}
