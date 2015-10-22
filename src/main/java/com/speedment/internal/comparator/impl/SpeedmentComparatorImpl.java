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
package com.speedment.internal.comparator.impl;

import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.comparator.SpeedmentComparator;
import java.util.Comparator;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 * @param <ENTITY> entity type
 * @param <V> value type
 */
public class SpeedmentComparatorImpl<ENTITY, V extends Comparable<? super V>> implements SpeedmentComparator<ENTITY, V> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, V> referenceField;
    private final NullOrder nullOrder;
    private boolean reversed;

    public SpeedmentComparatorImpl(FieldTrait field, ReferenceFieldTrait<ENTITY, V> referenceField, NullOrder nullOrder) {
        this.field = field;
        this.referenceField = referenceField;
        this.nullOrder = nullOrder;
    }

    @Override
    public FieldTrait getField() {
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
            switch (nullOrder) {
                case NONE: {
                    throw new NullPointerException("A field was null and null fields not allowed");
                }
                case FIRST: {
                    return applyReversed(-1);
                }
                case LAST: {
                    return applyReversed(1);
                }
            }
        }
        if (o2Value == null) {
            switch (nullOrder) {
                case NONE: {
                    throw new NullPointerException("A field was null and null fields not allowed");
                }
                case FIRST: {
                    return applyReversed(1);
                }
                case LAST: {
                    return applyReversed(-1);
                }
            }
        }
        return applyReversed(o1Value.compareTo(o2Value));
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
