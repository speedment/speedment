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
package com.speedment.runtime.internal.core.field.trait;

import com.speedment.runtime.field.predicate.StringSpeedmentPredicate;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.field.trait.StringFieldTrait;
import com.speedment.runtime.internal.core.field.predicate.impl.string.AlwaysFalseStringPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.AlwaysTrueStringPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.ContainsPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.EndsWithPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.EqualIgnoreCasePredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.IsEmptyPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.IsNotEmptyPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.IsNotNullStringPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.IsNullStringPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.NotContainsPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.NotEndsWithPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.NotEqualIgnoreCasePredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.NotStartsWithPredicate;
import com.speedment.runtime.internal.core.field.predicate.impl.string.StartsWithPredicate;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> the entity type
 * @author pemi
 */
public class StringFieldTraitImpl<ENTITY, D> implements StringFieldTrait<ENTITY, D> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, D, String> referenceField;

    public StringFieldTraitImpl(FieldTrait field, ReferenceFieldTrait<ENTITY, D, String> referenceField) {
        this.field = requireNonNull(field);
        this.referenceField = requireNonNull(referenceField);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> equalIgnoreCase(String value) {
        if (value == null) {
            return newIsNullPredicate();
        }
        return new EqualIgnoreCasePredicate<>(field, referenceField, value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> notEqualIgnoreCase(String value) {
        if (value == null) {
            return newIsNotNullPredicate();
        }
        return new NotEqualIgnoreCasePredicate<>(field, referenceField, value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> startsWith(String value) {
        if (value == null) {
            return newAlwaysFalsePredicate();
        }
        return new StartsWithPredicate<>(field, referenceField, value);
    }
    
    @Override
    public StringSpeedmentPredicate<ENTITY, D> notStartsWith(String value) {
        if (value == null) {
            return newAlwaysTruePredicate();
        }
        return new NotStartsWithPredicate<>(field, referenceField, value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> endsWith(String value) {
        if (value == null) {
            return newAlwaysFalsePredicate();
        }
        return new EndsWithPredicate<>(field, referenceField, value);
    }
    
    @Override
    public StringSpeedmentPredicate<ENTITY, D> notEndsWith(String value) {
        if (value == null) {
            return newAlwaysTruePredicate();
        }
        return new NotEndsWithPredicate<>(field, referenceField, value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> contains(String value) {
        if (value == null) {
            return newAlwaysFalsePredicate();
        }
        return new ContainsPredicate<>(field, referenceField, value);
    }
    
    @Override
    public StringSpeedmentPredicate<ENTITY, D> notContains(String value) {
        if (value == null) {
            return newAlwaysTruePredicate();
        }
        return new NotContainsPredicate<>(field, referenceField, value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> isEmpty() {
        return new IsEmptyPredicate<>(field, referenceField);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> isNotEmpty() {
        return new IsNotEmptyPredicate<>(field, referenceField);
    }

    private StringSpeedmentPredicate<ENTITY, D> newAlwaysFalsePredicate() {
        return new AlwaysFalseStringPredicate<>(field, referenceField);
    }

    private StringSpeedmentPredicate<ENTITY, D> newAlwaysTruePredicate() {
        return new AlwaysTrueStringPredicate<>(field, referenceField);
    }

    private StringSpeedmentPredicate<ENTITY, D> newIsNullPredicate() {
        return new IsNullStringPredicate<>(field, referenceField);
    }

    private StringSpeedmentPredicate<ENTITY, D> newIsNotNullPredicate() {
        return new IsNotNullStringPredicate<>(field, referenceField);
    }

}
