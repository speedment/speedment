package com.speedment.runtime.core.internal.component.sql.override.optimized.doubles;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.doubles.DoubleCountTerminator;
import static com.speedment.runtime.core.internal.component.sql.override.optimized.util.CountUtil.countHelper;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.DoublePipeline;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class OptimizedDoubleCountTerminator<ENTITY> implements DoubleCountTerminator<ENTITY> {

    private OptimizedDoubleCountTerminator() {
    }

    @Override
    public <T> long apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final DoublePipeline pipeline
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        return countHelper(
            info,
            pipeline,
            () -> DoubleCountTerminator.<ENTITY>defaultTerminator().apply(info, sqlStreamTerminator, pipeline)
        );
    }

    private static final DoubleCountTerminator<?> INSTANCE = new OptimizedDoubleCountTerminator<>();

    @SuppressWarnings("unchecked")
    public static <ENTITY> DoubleCountTerminator<ENTITY> create() {
        return (DoubleCountTerminator<ENTITY>) INSTANCE;
    }

}
