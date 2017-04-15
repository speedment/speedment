package com.speedment.runtime.core.internal.component.sql.override.optimized.longs;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.longs.LongCountTerminator;
import static com.speedment.runtime.core.internal.component.sql.override.optimized.util.CountUtil.countHelper;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.LongPipeline;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class OptimizedLongCountTerminator<ENTITY> implements LongCountTerminator<ENTITY> {

    private OptimizedLongCountTerminator() {
    }

    @Override
    public <T> long apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final LongPipeline pipeline
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        return countHelper(
            info,
            pipeline,
            () -> LongCountTerminator.<ENTITY>defaultTerminator().apply(info, sqlStreamTerminator, pipeline)
        );
    }

    public static final LongCountTerminator<?> INSTANCE = new OptimizedLongCountTerminator<>();

    @SuppressWarnings("unchecked")
    public static <ENTITY> LongCountTerminator<ENTITY> create() {
        return (LongCountTerminator<ENTITY>) INSTANCE;
    }

}
