package com.speedment.internal.core.stream.builder.action.trait;

import java.util.Comparator;

/**
 *
 * @author Per Minborg
 */
public interface HasComparator<T> {

    Comparator<? super T> getComparator();
    
}
