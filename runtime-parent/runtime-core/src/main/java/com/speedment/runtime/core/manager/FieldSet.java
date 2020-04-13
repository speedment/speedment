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
package com.speedment.runtime.core.manager;

import com.speedment.runtime.core.internal.manager.FieldSetImpl;
import com.speedment.runtime.field.Field;

import java.util.Arrays;
import java.util.Collection;

public interface FieldSet<ENTITY> extends HasLabelSet<ENTITY> {

    /**
     * Creates a new FieldSet with the given field removed from the original set.
     *
     * @param field the field to remove
     * @return a new FieldSet with the given field removed
     */
    FieldSet<ENTITY> except(Field<ENTITY> field);

    /**
     * Creates a new FieldSet with the given field added to the original set.
     *
     * @param field the field to add
     * @return a new FieldSet with an added field
     */
    FieldSet<ENTITY> and(Field<ENTITY> field);


    /**
     * Creates a new FieldSet that represents the set of all fields of an entity except for the given ones
     *
     * @param fields the fields to exclude from the set
     * @param <ENTITY> the type of entity
     * @return a new FieldSet that represents the set of all fields of an entity except for the given ones
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    static <ENTITY> FieldSet<ENTITY> allExcept(Field<ENTITY>... fields) {
        return new FieldSetImpl<>(Arrays.stream(fields)).negate();
    }

    /**
     * Creates a new FieldSet that represents the given set of fields
     *
     * @param fields the fields to include in the set
     * @param <ENTITY> the type of entity
     * @return a new FieldSet that represents the set of the given fields
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    static <ENTITY> FieldSet<ENTITY> of(Field<ENTITY>... fields) {
        return new FieldSetImpl<>(Arrays.stream(fields));
    }

    /**
     * Creates a new FieldSet that represents the set of all fields of an entity except for the given ones
     *
     * @param fields the fields to exclude from the set
     * @param <ENTITY> the type of entity
     * @return a new FieldSet that represents the set of all fields of an entity except for the given ones
     */
    static <ENTITY> FieldSet<ENTITY> allExcept(Collection<Field<ENTITY>> fields) {
        return new FieldSetImpl<>(fields.stream()).negate();
    }

    /**
     * Creates a new FieldSet that represents the given set of fields
     *
     * @param fields the fields to include in the set
     * @param <ENTITY> the type of entity
     * @return a new FieldSet that represents the set of the given fields
     */
    static <ENTITY> FieldSet<ENTITY> of(Collection<Field<ENTITY>> fields) {
        return new FieldSetImpl<>(fields.stream());
    }

    /**
     * Returns a FieldSet that represents all the fields of the given entity
     * @param classToken class token used to determine entity type
     * @param <ENTITY> the type of entity
     * @return a FieldSet that represents all the fields of the given entity
     */
    @SuppressWarnings("unchecked")
    static <ENTITY> FieldSet<ENTITY> allOf(Class<ENTITY> classToken) {
        return (FieldSet<ENTITY>) FieldSetImpl.ALL;
    }

    /**
     * Returns a FieldSet that represents the empty set of fields of the given entity
     * @param classToken class token used to determine entity type
     * @param <ENTITY> the type of entity
     * @return a FieldSet that represents the empty set of fields of the given entity
     */
    @SuppressWarnings("unchecked")
    static <ENTITY> FieldSet<ENTITY> noneOf(Class<ENTITY> classToken) {
        return (FieldSet<ENTITY>) FieldSetImpl.NONE;
    }

}
