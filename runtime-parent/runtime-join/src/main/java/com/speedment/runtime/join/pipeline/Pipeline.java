package com.speedment.runtime.join.pipeline;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public interface Pipeline {

    /**
     * Creates and returns a new Stream with the Stages in this Pipeline.
     *
     * @return a new Stream with the Stages in this Pipeline
     */
    Stream<Stage<?>> stages();

    /**
     * Returns the degree of the join pipeline (i.e. the number of tables in the
     * join).
     *
     * @return the degree of the join pipeline (i.e. the number of tables in the
     * join)
     */
    int degree();

    /**
     * Returns the Stage for the given {@code index}. The {@code index} shall be
     * in the range 2 (inclusive) and {@link #degree() } (exclusive).
     *
     * @param index for which the corresponding Stage shall be retrieved
     * @return the Stage for the given {@code index}
     *
     * @throws IndexOutOfBoundsException if the given {@code index} is less than
     * 2 or equal or grater than {@link #degree() }
     */
    Stage<?> get(int index);

}
