package com.speedment.runtime.internal.field.predicate;

import com.speedment.runtime.field.predicate.Inclusion;

/**
 * A common interface for predicates that test if an item is located
 * between two other items. This is useful for determining which 
 * inclusion strategy is expected from the stream.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface BetweenPredicate {
    
    /**
     * Returns the inclusion strategy used in the predicate.
     * 
     * @return  the inclusion strategy
     */
    Inclusion getInclusion();
    
}