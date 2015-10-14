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
package com.speedment.internal.core.field.trait;

import com.speedment.internal.core.field.predicate.impl.string.ContainsPredicate;
import com.speedment.internal.core.field.predicate.impl.string.EndsWithPredicate;
import com.speedment.internal.core.field.predicate.impl.string.EqualIgnoreCasePredicate;
import com.speedment.internal.core.field.predicate.impl.string.IsEmptyPredicate;
import com.speedment.internal.core.field.predicate.impl.string.IsNotEmptyPredicate;
import com.speedment.internal.core.field.predicate.impl.string.NotEqualIgnoreCasePredicate;
import com.speedment.internal.core.field.predicate.impl.string.StartsWithPredicate;
import com.speedment.field.methods.Getter;
import com.speedment.field.trait.StringFieldTrait;
import com.speedment.field.predicate.StringSpeedmentPredicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.core.field.predicate.impl.string.AlwaysFalseStringPredicate;
import com.speedment.internal.core.field.predicate.impl.string.IsNotNullStringPredicate;
import com.speedment.internal.core.field.predicate.impl.string.IsNullStringPredicate;

/**
 * @param <ENTITY> the entity type
 * @author pemi
 */
public class StringFieldTraitImpl<ENTITY> implements StringFieldTrait<ENTITY> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, String> referenceField;
    private final StringSpeedmentPredicate<ENTITY> alwaysFalsePredicate;
    private final StringSpeedmentPredicate<ENTITY> isNullPredicate;
    private final StringSpeedmentPredicate<ENTITY> isNotNullPredicate;

    public StringFieldTraitImpl(FieldTrait field, ReferenceFieldTrait<ENTITY, String> referenceField) {
        this.field = field;
        this.referenceField = referenceField;
        this.alwaysFalsePredicate = new AlwaysFalseStringPredicate<>(field, referenceField);
        this.isNullPredicate = new IsNullStringPredicate<>(field, referenceField);
        this.isNotNullPredicate = new IsNotNullStringPredicate<>(field, referenceField);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY> equalIgnoreCase(String value) {
        if (value == null) {
            return isNullPredicate;
        }
        return new EqualIgnoreCasePredicate<>(field, referenceField, value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY> notEqualIgnoreCase(String value) {
        if (value == null) {
            return isNotNullPredicate;
        }
        return new NotEqualIgnoreCasePredicate<>(field, referenceField, value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY> startsWith(String value) {
        if (value == null) {
            return alwaysFalsePredicate;
        }
        return new StartsWithPredicate<>(field, referenceField, value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY> endsWith(String value) {
        if (value == null) {
            return alwaysFalsePredicate;
        }
        return new EndsWithPredicate<>(field, referenceField, value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY> contains(String value) {
        if (value == null) {
            return alwaysFalsePredicate;
        }
        return new ContainsPredicate<>(field, referenceField, value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY> isEmpty() {
        return new IsEmptyPredicate<>(field, referenceField);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY> isNotEmpty() {
        return new IsNotEmptyPredicate<>(field, referenceField);
    }

}
