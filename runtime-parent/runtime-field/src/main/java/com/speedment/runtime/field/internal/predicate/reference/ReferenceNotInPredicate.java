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

import java.util.Set;

import static com.speedment.runtime.field.predicate.PredicateType.NOT_IN;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * @param <V>       the value type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class ReferenceNotInPredicate<ENTITY, D, V extends Comparable<? super V>>
extends AbstractFieldPredicate<ENTITY, HasReferenceValue<ENTITY, D, V>>
implements Tuple1<Set<V>> {

    private final Set<V> set;

    public ReferenceNotInPredicate(HasReferenceValue<ENTITY, D, V> field, Set<V> values) {
        super(NOT_IN, field, entity -> {
            final V value = field.get(entity);
            return value != null && !values.contains(value);
        });
        this.set = requireNonNull(values);
    }

    @Override
    public Set<V> get0() {
        return set;
    }

    @Override
    public ReferenceInPredicate<ENTITY, D, V> negate() {
        return new ReferenceInPredicate<>(getField(), set);
    }
}