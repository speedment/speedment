package com.speedment.runtime.core.internal.component.sql.override.def.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.FindFirstTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultFindFirstTerminator<ENTITY> implements FindFirstTerminator<ENTITY> {

    private DefaultFindFirstTerminator() {
    }

    @Override
    public <T> Optional<T> apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final ReferencePipeline<T> pipeline
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        return sqlStreamTerminator.optimize(pipeline).getAsReferenceStream().findFirst();
    }

    public static final FindFirstTerminator<?> DEFAULT = new DefaultFindFirstTerminator<>();

}
