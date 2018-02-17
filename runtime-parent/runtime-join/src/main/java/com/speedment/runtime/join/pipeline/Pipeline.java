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
}
