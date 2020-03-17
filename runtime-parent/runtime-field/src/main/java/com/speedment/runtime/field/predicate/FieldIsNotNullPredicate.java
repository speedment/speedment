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
package com.speedment.runtime.field.predicate;

import com.speedment.runtime.compute.expression.predicate.IsNotNull;

/**
 * Specialized {@link FieldPredicate} that also implements {@link IsNotNull}
 * from the {@code runtime-compute}-module.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface FieldIsNotNullPredicate<ENTITY, T>
extends FieldPredicate<ENTITY>, IsNotNull<ENTITY, T> {

    @Override
    boolean test(ENTITY value);

    @Override
    FieldIsNullPredicate<ENTITY, T> negate();

    @Override
    default PredicateType getPredicateType() {
        return PredicateType.IS_NOT_NULL;
    }

    @Override
    default boolean applyAsBoolean(ENTITY object) {
        return test(object);
    }
}