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

import com.speedment.field2.methods.Getter;
import static com.speedment.field2.predicate.PredicateType.ALWAYS_TRUE;
import com.speedment.field2.predicate.SpeedmentPredicate;
import com.speedment.field2.trait.FieldTrait;
import com.speedment.internal.core.field2.predicate.iface.type.BinaryOperation;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public class AlwaysTrueStringPredicate<ENTITY> extends BaseStringPredicate<ENTITY>
    implements SpeedmentPredicate<ENTITY, String>, BinaryOperation<String> {

    public AlwaysTrueStringPredicate(FieldTrait field, Getter<ENTITY, String> getter, String operand0) {
        super(ALWAYS_TRUE, field, getter, null, (a, b) -> true);
    }

}
