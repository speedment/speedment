package com.speedment.runtime.internal.field.predicate;

import com.speedment.runtime.field.Inclusion;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface BetweenPredicate {
    
    Inclusion getInclusion();
    
}
