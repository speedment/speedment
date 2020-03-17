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
package com.speedment.runtime.field.internal.predicate.shorts;

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasShortValue;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of shorts.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public final class ShortInPredicate<ENTITY, D> 
extends AbstractFieldPredicate<ENTITY, HasShortValue<ENTITY, D>> 
implements Tuple1<Set<Short>> {
    
    private final Set<Short> set;
    
    public ShortInPredicate(HasShortValue<ENTITY, D> field, Set<Short> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsShort(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Short> get0() {
        return set;
    }
    
    @Override
    public ShortNotInPredicate<ENTITY, D> negate() {
        return new ShortNotInPredicate<>(getField(), set);
    }
}