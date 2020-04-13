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
package com.speedment.runtime.join.trait;

import com.speedment.runtime.field.trait.HasComparableOperators;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type of entity
 */
public interface HasOn<ENTITY> {

    /**
     * Adds the given {@code originalField} to an operation where the
     * {@code originalField} belongs to a previous entered manager that are used
     * for joining elements.
     *
     * @param <V> type that the field can hold
     * @param <FIELD> type of the field
     * @param originalField joined field
     * @return a builder with the modified operation
     *
     * @throws NullPointerException if the provided {@code originalField } is
     * {@code null}
     * @throws IllegalArgumentException if the provided {@code originalField}
     * does not belong to a manager that is previously entered into the builder.
     */
    <V extends Comparable<? super V>, FIELD extends HasComparableOperators<? extends ENTITY, V>> Object on(FIELD originalField);

}
