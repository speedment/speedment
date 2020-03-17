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
package com.speedment.runtime.field.internal.predicate.doubles;

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasDoubleValue;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of doubles.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public final class DoubleInPredicate<ENTITY, D> 
extends AbstractFieldPredicate<ENTITY, HasDoubleValue<ENTITY, D>> 
implements Tuple1<Set<Double>> {
    
    private final Set<Double> set;
    
    public DoubleInPredicate(HasDoubleValue<ENTITY, D> field, Set<Double> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsDouble(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Double> get0() {
        return set;
    }
    
    @Override
    public DoubleNotInPredicate<ENTITY, D> negate() {
        return new DoubleNotInPredicate<>(getField(), set);
    }
}