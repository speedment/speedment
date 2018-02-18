package com.speedment.runtime.join.trait;

import com.speedment.common.function.Function5;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.pipeline.Pipeline;

/**
 *
 * @author Per Minborg
 */
public interface HasCreateJoin5 {

    /**
     * Creates and returns a new Join object using the provided {@code pipeline}
     * whereby elements in the returned Join's {@link Join#stream() } method
     * will be constructed using the provided {@code constructor}.
     *
     * @param <T1> entity type of the first table
     * @param <T2> entity type of the second table
     * @param <T3> entity type of the third table
     * @param <T4> entity type of the fourth table
     * @param <T5> entity type of the fifth table
     * @param <T> stream type in returned Join object's stream method
     * @param p pipeline with information on the joined tables
     * @param constructor to be applied by the returned Join objects stream
     * method
     * @param t1 identifier of the first table
     * @param t2 identifier of the second table
     * @param t3 identifier of the third table
     * @param t4 identifier of the fourth table
     * @param t5 identifier of the fifths table
     * @return a new Join object
     *
     * @throws NullPointerException if any of the provided arguments are
     * {@code null}
     */
    <T1, T2, T3, T4, T5, T> Join<T> createJoin(
        Pipeline p,
        Function5<T1, T2, T3, T4, T5, T> constructor,
        TableIdentifier<T1> t1,
        TableIdentifier<T2> t2,
        TableIdentifier<T3> t3,
        TableIdentifier<T4> t4,
        TableIdentifier<T5> t5
    );
}
