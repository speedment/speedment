package com.speedment.runtime.core.internal.component.sql.override.def.longs;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.longs.LongCountTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.LongPipeline;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultLongCountTerminator<ENTITY> implements LongCountTerminator<ENTITY> {

    private DefaultLongCountTerminator() {
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
        return sqlStreamTerminator.optimize(pipeline).getAsLongStream().count();
    }

    public static final LongCountTerminator<?> DEFAULT = new DefaultLongCountTerminator<>();

}
