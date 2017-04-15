package com.speedment.runtime.core.component.sql.override.ints;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.ints.DefaultIntCountTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.IntPipeline;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface IntCountTerminator<ENTITY> extends IntTerminator {

    <T> long apply(
        SqlStreamOptimizerInfo<ENTITY> info,        
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        IntPipeline pipeline
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> IntCountTerminator<ENTITY> defaultTerminator() {
        return (IntCountTerminator<ENTITY>) DEFAULT;
    }

}
