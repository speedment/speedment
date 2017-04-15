package com.speedment.runtime.core.internal.component.sql.override.optimized.ints;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.ints.IntCountTerminator;
import static com.speedment.runtime.core.internal.component.sql.override.optimized.util.CountUtil.countHelper;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.IntPipeline;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class OptimizedIntCountTerminator<ENTITY> implements IntCountTerminator<ENTITY> {

    private OptimizedIntCountTerminator() {
    }

    @Override
    public <T> long apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final IntPipeline pipeline
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        return countHelper(
            info,
            pipeline,
            () -> IntCountTerminator.<ENTITY>defaultTerminator().apply(info, sqlStreamTerminator, pipeline)
        );
    }

    public static final IntCountTerminator<?> INSTANCE = new OptimizedIntCountTerminator<>();

    @SuppressWarnings("unchecked")
    public static <ENTITY> IntCountTerminator<ENTITY> create() {
        return (IntCountTerminator<ENTITY>) INSTANCE;
    }

}
