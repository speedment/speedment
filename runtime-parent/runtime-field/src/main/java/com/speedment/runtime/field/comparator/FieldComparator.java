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
package com.speedment.runtime.field.comparator;

import com.speedment.runtime.field.trait.HasComparableOperators;
import java.util.Comparator;

/**
 * A specialized {@link Comparator} that contains meta data information about 
 * the field that is being compared.
 * 
 * @param <ENTITY>  the entity type
 * @param <V>       the value type
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface FieldComparator<ENTITY, V extends Comparable<? super V>> 
extends Comparator<ENTITY> {

    /**
     * Returns the field that created this comparator.
     * 
     * @return  the field
     */
    HasComparableOperators<ENTITY, V> getField();

    @Override
    FieldComparator<ENTITY, V> reversed();
    
    /**
     * Returns {@code true} if this comparator reverses the natural order of the
     * values in the current field. A reversed order descends from high values
     * to low.
     * 
     * @return  {@code true} if reversed (descending), else {@code false}
     */
    boolean isReversed();
    
}