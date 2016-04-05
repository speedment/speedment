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

import static com.speedment.field.predicate.PredicateType.ENDS_WITH;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.core.field.predicate.iface.type.BinaryOperation;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public class EndsWithPredicate<ENTITY, D> extends BaseStringPredicate<ENTITY, D>
        implements SpeedmentPredicate<ENTITY, D, String>, BinaryOperation<String> {

    public EndsWithPredicate(FieldTrait field, ReferenceFieldTrait<ENTITY, D, String> referenceField, String operand0) {
        super(ENDS_WITH, field, referenceField, operand0, ENDS_WITH_PREDICATE);
    }

}
