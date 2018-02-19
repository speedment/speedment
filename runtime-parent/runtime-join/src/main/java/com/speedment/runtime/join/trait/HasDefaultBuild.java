package com.speedment.runtime.join.trait;

import com.speedment.common.tuple.Tuple;
import com.speedment.runtime.join.Join;

/**
 *
 * @author Per Minborg
 * @param <R> RETURN TYPE
 *
 */
public interface HasDefaultBuild<R extends Tuple> {

    /**
     * Creates and returns a new Join object where elements in the Join object's
     * stream method is of a default {@link Tuple} type.
     *
     * @return a new Join object where elements in the Join object's stream
     * method is of a default {@link Tuple} type
     *
     * @throws IllegalStateException if fields that are added via the {@code on()
     * } method refers to tables that are not a part of the join.
     */
    Join<R> build();

}
