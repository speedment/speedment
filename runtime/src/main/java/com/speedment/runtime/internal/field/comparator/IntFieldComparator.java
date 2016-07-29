package com.speedment.runtime.internal.field.comparator;

import com.speedment.runtime.field.trait.HasIntValue;
import java.util.Comparator;

/**
 * A predicate that evaluates if a value is between two ints.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public interface IntFieldComparator<ENTITY, D>  extends Comparator<ENTITY> {
    
    /**
     * Gets the field that is being compared.
     * 
     * @return the compared field
     */
    HasIntValue<ENTITY, D> getField();
    
    /**
     * Returns if this {@code Comparator} is reversed.
     * 
     * @return if this is reversed
     */
    boolean isReversed();
}