package com.speedment.runtime.join.internal.pipeline;

import com.speedment.runtime.join.pipeline.Stage;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;
import com.speedment.runtime.join.pipeline.Pipeline;

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

    @Override
    public int degree() {
        return stages.size();
    }

    @Override
    public Stage<?> get(int index) {
        return stages.get(index);
    }

}
