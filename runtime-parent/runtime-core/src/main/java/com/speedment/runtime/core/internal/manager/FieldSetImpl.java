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
package com.speedment.runtime.core.internal.manager;

import com.speedment.runtime.core.manager.FieldSet;
import com.speedment.runtime.field.Field;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FieldSetImpl<ENTITY> implements FieldSet<ENTITY> {
    public static final FieldSetImpl<?> ALL = new FieldSetImpl<>(unused -> true);
    public static final FieldSetImpl<?> NONE = new FieldSetImpl<>(unused -> false);

    private final Predicate<String> includedId;

    public FieldSetImpl(Predicate<String> includedId) {
        this.includedId = includedId;
    }

    public FieldSetImpl(Stream<Field<ENTITY>> fieldStream) {
        Set<String> ids = fieldStream.map(f -> f.identifier().getColumnId()).collect(Collectors.toSet());
        switch (ids.size()) {
            case 0:
                includedId = unused -> false;
                break;
            case 1:
                final String id = ids.iterator().next();
                includedId = id::equals;
                break;
            default:
                includedId = ids::contains;
        }
    }

    @Override
    public boolean test(String id) {
        return includedId.test(id);
    }

    @Override
    public FieldSetImpl<ENTITY> negate() {
        return new FieldSetImpl<>(includedId.negate());
    }

    @Override
    public FieldSet<ENTITY> except(Field<ENTITY> field) {
        String id = field.identifier().getColumnId();
        return new FieldSetImpl<>(s -> !id.equals(s) && test(s));
    }

    @Override
    public FieldSet<ENTITY> and(Field<ENTITY> field) {
        String id = field.identifier().getColumnId();
        return new FieldSetImpl<>(s -> id.equals(s) || test(s));
    }
}
