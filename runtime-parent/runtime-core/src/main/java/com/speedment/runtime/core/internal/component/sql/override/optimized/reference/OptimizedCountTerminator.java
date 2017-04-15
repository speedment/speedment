package com.speedment.runtime.core.internal.component.sql.override.optimized.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.CountTerminator;
import static com.speedment.runtime.core.internal.component.sql.override.optimized.util.CountUtil.countHelper;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class OptimizedCountTerminator<ENTITY> implements CountTerminator<ENTITY> {

    private OptimizedCountTerminator() {
    }

    @Override
    public <T> long apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final ReferencePipeline<T> pipeline
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        return countHelper(
            info,
            pipeline,
            () -> CountTerminator.<ENTITY>defaultTerminator().apply(info, sqlStreamTerminator, pipeline)
        );
    }

    public static final CountTerminator<?> INSTANCE = new OptimizedCountTerminator<>();

    @SuppressWarnings("unchecked")
    public static <ENTITY> CountTerminator<ENTITY> create() {
        return (CountTerminator<ENTITY>) INSTANCE;
    }

}
