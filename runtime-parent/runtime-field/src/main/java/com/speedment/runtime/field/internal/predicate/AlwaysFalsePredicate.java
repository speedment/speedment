/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.field.internal.predicate;

import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.PredicateType;

/**
 * A special implementation of {@code Predicate} that always returns false.
 *
 * @param <ENTITY> the entity type
 * @param <FIELD> the field type
 *
 * @author Per Minborg
 * @since 2.2.0
 */
public final class AlwaysFalsePredicate<ENTITY, FIELD extends Field<ENTITY>>
    extends AbstractFieldPredicate<ENTITY, FIELD> {

    public AlwaysFalsePredicate(FIELD field) {
        super(PredicateType.ALWAYS_FALSE, field, entity -> false);
    }

    @Override
    public FieldPredicate<ENTITY> negate() {
        return new AlwaysTruePredicate<>(getField());
    }
}
