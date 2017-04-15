package com.speedment.runtime.core.component.sql.override.longs;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.longs.DefaultLongCountTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.LongPipeline;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface LongCountTerminator<ENTITY> extends LongTerminator {

    <T> long apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        LongPipeline pipeline
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> LongCountTerminator<ENTITY> defaultTerminator() {
        return (LongCountTerminator<ENTITY>) DEFAULT;
    }

}
