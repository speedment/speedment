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
package com.speedment.runtime.internal.core.field.predicate.impl.reference;

import static com.speedment.runtime.field.predicate.PredicateType.IS_NULL;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.internal.core.field.predicate.iface.type.UnaryOperation;
import com.speedment.runtime.internal.core.field.predicate.impl.SpeedmentPredicateImpl;
import java.util.Objects;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public class IsNullPredicate<ENTITY, D, V>
        extends SpeedmentPredicateImpl<ENTITY, D, V>
        implements SpeedmentPredicate<ENTITY, D, V>, UnaryOperation {

    public IsNullPredicate(FieldTrait field, ReferenceFieldTrait<ENTITY, D, V> referenceField) {
        super(IS_NULL, field, referenceField);
    }

    @Override
    public boolean testField(V fieldValue) {
        return Objects.isNull(fieldValue);
    }

}
