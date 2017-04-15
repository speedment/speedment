package com.speedment.runtime.core.internal.component.sql.override.def.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.NoneMatchTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultNoneMatchTerminator<ENTITY> implements NoneMatchTerminator<ENTITY> {

    private DefaultNoneMatchTerminator() {
    }

    @Override
    public <T> boolean apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final ReferencePipeline<T> pipeline,
        final Predicate<? super T> predicate
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        requireNonNull(predicate);
        return sqlStreamTerminator.optimize(pipeline).getAsReferenceStream().noneMatch(predicate);
    }

    public static final NoneMatchTerminator<?> DEFAULT = new DefaultNoneMatchTerminator<>();

}
