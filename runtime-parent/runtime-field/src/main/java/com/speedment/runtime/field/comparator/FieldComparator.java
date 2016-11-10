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
    
}