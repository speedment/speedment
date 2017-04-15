package com.speedment.runtime.core.internal.component.sql.override.def.ints;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.ints.IntCountTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.IntPipeline;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultIntCountTerminator<ENTITY> implements IntCountTerminator<ENTITY> {

    private DefaultIntCountTerminator() {
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
        return sqlStreamTerminator.optimize(pipeline).getAsIntStream().count();
    }

    public static final IntCountTerminator<?> DEFAULT = new DefaultIntCountTerminator<>();

}
