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
package com.speedment.runtime.core.internal.field.comparator;

import com.speedment.runtime.core.field.trait.HasDoubleValue;
import java.util.Comparator;
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
public final class DoubleFieldComparatorImpl<ENTITY, D> implements DoubleFieldComparator<ENTITY, D> {
    
    private final HasDoubleValue<ENTITY, D> field;
    private boolean reversed;
    
    public DoubleFieldComparatorImpl(HasDoubleValue<ENTITY, D> field) {
        this.field    = requireNonNull(field);
        this.reversed = false;
    }
    
    @Override
    public HasDoubleValue<ENTITY, D> getField() {
        return field;
    }
    
    @Override
    public boolean isReversed() {
        return reversed;
    }
    
    @Override
    public Comparator<ENTITY> reversed() {
        reversed = !reversed;
        return this;
    }
    
    @Override
    public int compare(ENTITY first, ENTITY second) {
        requireNonNulls(first, second);
        final double a = field.getAsDouble(first);
        final double b = field.getAsDouble(second);
        return applyReversed(a - b);
    }
    
    private int applyReversed(double compare) {
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