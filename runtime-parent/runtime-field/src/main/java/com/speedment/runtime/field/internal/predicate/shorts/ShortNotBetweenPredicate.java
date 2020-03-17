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
import com.speedment.common.tuple.Tuple2;
import com.speedment.runtime.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.predicate.trait.HasInclusion;
import com.speedment.runtime.field.trait.HasShortValue;
import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is not between two shorts.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.11
 */
@GeneratedCode(value = "Speedment")
public final class ShortNotBetweenPredicate<ENTITY, D> 
extends AbstractFieldPredicate<ENTITY, HasShortValue<ENTITY, D>> 
implements HasInclusion,
          Tuple2<Short, Short> {
    
    private final short start;
    private final short end;
    private final Inclusion inclusion;
    
    public ShortNotBetweenPredicate(
            HasShortValue<ENTITY, D> field,
            short start,
            short end,
            Inclusion inclusion) {
        super(PredicateType.NOT_BETWEEN, field, entity -> {
            final short fieldValue = field.getAsShort(entity);
            
            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE :
                    return (start >= fieldValue || end <= fieldValue);
                
                case START_EXCLUSIVE_END_INCLUSIVE :
                    return (start >= fieldValue || end < fieldValue);
                
                case START_INCLUSIVE_END_EXCLUSIVE :
                    return (start > fieldValue || end <= fieldValue);
                
                case START_INCLUSIVE_END_INCLUSIVE :
                    return (start > fieldValue || end < fieldValue);
                
                default : throw new IllegalStateException("Inclusion unknown: " + inclusion);
            }
        });
        
        this.start     = start;
        this.end       = end;
        this.inclusion = requireNonNull(inclusion);
    }
    
    @Override
    public Short get0() {
        return start;
    }
    
    @Override
    public Short get1() {
        return end;
    }
    
    @Override
    public Inclusion getInclusion() {
        return inclusion;
    }
    
    @Override
    public ShortBetweenPredicate<ENTITY, D> negate() {
        return new ShortBetweenPredicate<>(getField(), start, end, inclusion);
    }
}