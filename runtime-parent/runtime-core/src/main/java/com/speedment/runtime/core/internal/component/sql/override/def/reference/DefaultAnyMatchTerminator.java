package com.speedment.runtime.core.internal.component.sql.override.def.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.AnyMatchTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultAnyMatchTerminator<ENTITY> implements AnyMatchTerminator<ENTITY> {

    private DefaultAnyMatchTerminator() {
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
        return sqlStreamTerminator.optimize(pipeline).getAsReferenceStream().anyMatch(predicate);
    }

    public static final AnyMatchTerminator<?> DEFAULT = new DefaultAnyMatchTerminator<>();

}
