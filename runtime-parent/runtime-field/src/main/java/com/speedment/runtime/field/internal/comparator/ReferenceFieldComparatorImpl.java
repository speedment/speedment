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
package com.speedment.runtime.field.internal.comparator;

import com.speedment.runtime.field.comparator.NullOrder;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.comparator.FieldComparator;
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
public final class ReferenceFieldComparatorImpl<ENTITY, D, V extends Comparable<? super V>> implements ReferenceFieldComparator<ENTITY, D, V> {

    private final ComparableField<ENTITY, D, V> referenceField;
    private final NullOrder nullOrder;
    
    private boolean reversed;

    public ReferenceFieldComparatorImpl(ComparableField<ENTITY, D, V> referenceField, NullOrder nullOrder) {
        this.referenceField = referenceField;
        this.nullOrder = nullOrder;
    }

    @Override
    public ComparableField<ENTITY, D, V> getField() {
        return referenceField;
    }

    @Override
    public boolean isReversed() {
        return reversed;
    }

    @Override
    public FieldComparator<ENTITY, V> reversed() {
        reversed = !reversed;
        return this;
    }

    @Override
    public int compare(ENTITY o1, ENTITY o2) {
        final V o1Value = referenceField.getter().apply(requireNonNull(o1));
        final V o2Value = referenceField.getter().apply(requireNonNull(o2));
        if (o1Value == null && o2Value == null) {
            if (NullOrder.NONE == nullOrder) {
                throw new NullPointerException("Both fields were null and null fields not allowed");
            }
            return 0;
        }
        if (o1Value == null) {
            return forNull(Parameter.FIRST);
        }
        if (o2Value == null) {
            return forNull(Parameter.SECOND);
        }
        return applyReversed(o1Value.compareTo(o2Value));
    }

    private enum Parameter {
        FIRST, SECOND
    }

    private int forNull(Parameter parameter) {
        final int firstOutcome = Parameter.FIRST.equals(parameter) ? -1 : 1;
        final int lastOutcome = -firstOutcome;
        switch (nullOrder) {
            case NONE:
                throw new NullPointerException("A field was null and null fields not allowed");
            case FIRST:
                return applyReversed(firstOutcome);
            case LAST:
                return applyReversed(lastOutcome);
            default:
                throw new IllegalStateException("Illegal NullOrder");
        }
    }

    private int applyReversed(int compare) {
        if (!reversed) {
            return compare;
        }
        if (compare == 0) {
            return 0;
        } else if (compare > 0) {
            return -1;
        } else {
            return 1;
        }
    }

}
