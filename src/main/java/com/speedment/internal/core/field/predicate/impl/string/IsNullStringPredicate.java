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

import static com.speedment.field.predicate.PredicateType.IS_NULL;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.core.field.predicate.iface.type.BinaryOperation;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public class IsNullStringPredicate<ENTITY> extends BaseStringPredicate<ENTITY>
    implements SpeedmentPredicate<ENTITY, String>, BinaryOperation<String> {

    public IsNullStringPredicate(FieldTrait field, ReferenceFieldTrait<ENTITY, String> referenceField) {
        super(IS_NULL, field, referenceField, null, (o, f) -> f == null);
    }

}
