package com.speedment.runtime.core.internal.component.sql.override.def.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.SpliteratorTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;
import java.util.Spliterator;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultSpliteratorTerminator<ENTITY> implements SpliteratorTerminator<ENTITY> {

    private DefaultSpliteratorTerminator() {
    }

    @Override
    public <T> Spliterator<T> apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final ReferencePipeline<T> pipeline
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        return sqlStreamTerminator.optimize(pipeline).getAsReferenceStream().spliterator();
    }

    public static final SpliteratorTerminator<?> DEFAULT = new DefaultSpliteratorTerminator<>();

}
