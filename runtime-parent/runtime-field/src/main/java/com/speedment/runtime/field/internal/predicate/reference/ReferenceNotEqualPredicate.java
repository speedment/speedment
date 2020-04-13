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
package com.speedment.runtime.field.internal.predicate.reference;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.runtime.field.trait.HasReferenceValue;

import java.util.Objects;

import static com.speedment.runtime.field.predicate.PredicateType.NOT_EQUAL;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * @param <V>       the value type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class ReferenceNotEqualPredicate<ENTITY, D, V extends Comparable<? super V>>
extends AbstractFieldPredicate<ENTITY, HasReferenceValue<ENTITY, D, V>>
implements Tuple1<V> {

    private final V value;

    public ReferenceNotEqualPredicate(HasReferenceValue<ENTITY, D, V> field, V value) {
        super(NOT_EQUAL, field, entity -> {
            final V v = field.get(entity);
            return v != null && !Objects.equals(v, value);
        });
        this.value = value;
    }

    @Override
    public V get0() {
        return value;
    }

    @Override
    public ReferenceEqualPredicate<ENTITY, D, V> negate() {
        return new ReferenceEqualPredicate<>(getField(), value);
    }
}