package com.speedment.runtime.core.internal.component.sql.override.def.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.CollectTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;
import java.util.stream.Collector;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultCollectTerminator<ENTITY> implements CollectTerminator<ENTITY> {

    private DefaultCollectTerminator() {
    }

    @Override
    public <T, R, A> R apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final ReferencePipeline<T> pipeline,
        final Collector<? super T, A, R> collector
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        requireNonNull(collector);
        return sqlStreamTerminator
            .optimize(pipeline)
            .getAsReferenceStream().collect(collector);
    }

    public static final CollectTerminator<?> DEFAULT = new DefaultCollectTerminator<>();

}
