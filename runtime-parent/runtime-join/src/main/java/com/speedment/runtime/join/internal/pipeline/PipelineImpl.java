package com.speedment.runtime.join.internal.pipeline;

import com.speedment.runtime.join.pipeline.Pipeline;
import com.speedment.runtime.join.pipeline.Stage;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class PipelineImpl implements Pipeline {

    private final List<Stage<?>> stages;

    public PipelineImpl(List<Stage<?>> stages) {
        this.stages = new ArrayList<>(requireNonNull(stages));
    }

    @Override
    public Stream<Stage<?>> stages() {
        return stages.stream();
    }

}
