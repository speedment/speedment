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
package com.speedment.runtime.field.internal.predicate.string;

import com.speedment.runtime.compute.trait.ToNullable;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.field.predicate.FieldIsNotNullPredicate;
import com.speedment.runtime.field.predicate.FieldIsNullPredicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.2
 */
public final class StringIsNullPredicate<ENTITY, D>
implements FieldIsNullPredicate<ENTITY, String> {

    private final StringField<ENTITY, D> field;

    public StringIsNullPredicate(StringField<ENTITY, D> field) {
        this.field = requireNonNull(field);
    }

    @Override
    public boolean test(ENTITY value) {
        return field.apply(value) == null;
    }

    @Override
    public FieldIsNotNullPredicate<ENTITY, String> negate() {
        return new StringIsNotNullPredicate<>(field);
    }

    @Override
    public ToNullable<ENTITY, String, ?> expression() {
        return field;
    }

    @Override
    public Field<ENTITY> getField() {
        return field;
    }
}
