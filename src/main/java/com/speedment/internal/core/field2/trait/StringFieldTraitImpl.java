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
package com.speedment.internal.core.field2.trait;

import com.speedment.internal.core.field2.predicate.impl.AlwaysFalsePredicate;
import com.speedment.internal.core.field2.predicate.impl.reference.IsNotNullPredicate;
import com.speedment.internal.core.field2.predicate.impl.reference.IsNullPredicate;
import com.speedment.internal.core.field2.predicate.impl.string.ContainsPredicate;
import com.speedment.internal.core.field2.predicate.impl.string.EndsWithPredicate;
import com.speedment.internal.core.field2.predicate.impl.string.EqualIgnoreCasePredicate;
import com.speedment.internal.core.field2.predicate.impl.string.IsEmptyPredicate;
import com.speedment.internal.core.field2.predicate.impl.string.IsNotEmptyPredicate;
import com.speedment.internal.core.field2.predicate.impl.string.NotEqualIgnoreCasePredicate;
import com.speedment.internal.core.field2.predicate.impl.string.StartsWithPredicate;
import com.speedment.field2.methods.Getter;
import java.util.function.Predicate;
import com.speedment.field2.trait.StringFieldTrait;

/**
 * @param <ENTITY> the entity type
 * @author pemi
 */
public class StringFieldTraitImpl<ENTITY> implements StringFieldTrait<ENTITY> {

    private final Getter<ENTITY, String> getter;
    private final AlwaysFalsePredicate<ENTITY, String> alwaysFalsePredicate;
    private final IsNullPredicate<ENTITY, String> isNullPredicate;
    private final IsNotNullPredicate<ENTITY, String> isNotNullPredicate;

    public StringFieldTraitImpl(Getter<ENTITY, String> getter) {
        this.getter = getter;
        this.alwaysFalsePredicate = new AlwaysFalsePredicate<>(getter);
        this.isNullPredicate = new IsNullPredicate<>(getter);
        this.isNotNullPredicate = new IsNotNullPredicate<>(getter);
    }

    @Override
    public Predicate<ENTITY> equalIgnoreCase(String value) {
        if (value == null) {
            return isNullPredicate;
        }
        return new EqualIgnoreCasePredicate<>(getter, value);
    }

    @Override
    public Predicate<ENTITY> notEqualIgnoreCase(String value) {
        if (value == null) {
            return isNotNullPredicate;
        }
        return new NotEqualIgnoreCasePredicate<>(getter, value);
    }

    @Override
    public Predicate<ENTITY> startsWith(String value) {
        if (value == null) {
            return alwaysFalsePredicate;
        }
        return new StartsWithPredicate<>(getter, value);
    }

    @Override
    public Predicate<ENTITY> endsWith(String value) {
        if (value == null) {
            return alwaysFalsePredicate;
        }
        return new EndsWithPredicate<>(getter, value);
    }

    @Override
    public Predicate<ENTITY> contains(String value) {
        if (value == null) {
            return alwaysFalsePredicate;
        }
        return new ContainsPredicate<>(getter, value);
    }

    @Override
    public Predicate<ENTITY> isEmpty() {
        return new IsEmptyPredicate<>(getter);
    }

    @Override
    public Predicate<ENTITY> isNotEmpty() {
        return new IsNotEmptyPredicate<>(getter);
    }

}
