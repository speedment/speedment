package com.speedment.runtime.join.trait;

import com.speedment.common.tuple.Tuple;
import com.speedment.runtime.join.Join;

/**
 *
 * @author Per Minborg
 * @param <T> RETURN TYPE
 *
 */
public interface HasDefaultBuild<T extends Tuple> {

    /**
     * Creates and returns a new Join object where elements in the Join object's
     * stream method is of a default {@link Tuple} type.
     *
     * @return new Join object where elements in the Join object's stream method
     * is of a default {@link Tuple} type
     */
    Join<T> build();

}
