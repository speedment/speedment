/**
 * 
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.field.internal.comparator;

import com.speedment.runtime.field.LongField;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.comparator.NullOrder;
import java.util.Objects;
import javax.annotation.Generated;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class LongFieldComparatorImpl<ENTITY, D> implements LongFieldComparator<ENTITY, D> {
    
    private final LongField<ENTITY, D> field;
    private final boolean reversed;
    
    public LongFieldComparatorImpl(LongField<ENTITY, D> field) {
        this(field, false);
    }
    
    public LongFieldComparatorImpl(LongField<ENTITY, D> field, boolean reversed) {
        this.field    = requireNonNull(field);
        this.reversed = reversed;
    }
    
    @Override
    public LongField<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public NullOrder getNullOrder() {
        return NullOrder.NONE;
    }
    
    @Override
    public boolean isReversed() {
        return reversed;
    }
    
    @Override
    public FieldComparator<ENTITY, Long> reversed() {
        return new LongFieldComparatorImpl<>(field, !reversed);
    }
    
    @Override
    public int compare(ENTITY first, ENTITY second) {
        requireNonNulls(first, second);
        final long a = field.getAsLong(first);
        final long b = field.getAsLong(second);
        return applyReversed(a - b);
    }
    
    @Override
    public int hashCode() {
        return (4049 + Objects.hashCode(this.field.identifier())) * 3109
            + Boolean.hashCode(reversed);
    }
    
    @Override
    public boolean equals(Object obj) {
        if      (this == obj) return true;
        else if (obj == null) return false;
        else if (!(obj instanceof FieldComparator)) return false;
        
        @SuppressWarnings("unchecked")
        final FieldComparator<ENTITY, Long> casted =
            (FieldComparator<ENTITY, Long>) obj;
        
        return reversed == casted.isReversed()
            && Objects.equals(
                field.identifier(),
                casted.getField().identifier()
            );
    }
    
    @Override
    public String toString() {
        return "(order by " + field.identifier() + " " +
            (reversed ? "descending" : "ascending") + ")";
    }
    
    private int applyReversed(long compare) {
        if (compare == 0) {
            return 0;
        } else {
            if (reversed) {
                if (compare > 0) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (compare > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }
}